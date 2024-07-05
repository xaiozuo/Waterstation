package com.waterstation.waterstation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.waterstation.waterstation.common.Result;
import com.waterstation.waterstation.entity.*;
import com.waterstation.waterstation.service.TbGroupPointFlowService;
import com.waterstation.waterstation.service.TbGroupService;
import com.waterstation.waterstation.service.TbPointtransactionrecordsService;
import com.waterstation.waterstation.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.waterstation.waterstation.common.Result.*;

@RestController
@Controller
@RequestMapping("/tb-group")
public class TbGroupController {
    @Autowired
    private TbGroupService tbGroupService;
    @Autowired
    private TbUserService tbUserService;
    @Autowired
    private TbPointtransactionrecordsService tbPointtransactionrecordsService;
    @Autowired
    private TbGroupPointFlowService tbGroupPointFlowService;
//    @GetMapping("/assessRole")
//    public String assessRole(@RequestBody Map<String, Object> requestParams) {
//        List<TbGroup> tbGroupList= tbGroupService.listByMap(requestParams);
//        for (TbGroup tbgroup : tbGroupList) {
//            return tbgroup.getGroupOwner();
//        }
//        return null;
//    }
    @GetMapping("/list")
    public List<TbGroup> list(
            Integer idFilter,
            String groupIdFilter,
            String groupNameFilter,
            String groupOwnerFilter) {
        QueryWrapper<TbGroup> queryWrapper = new QueryWrapper<>();
        if (idFilter != null) {
            queryWrapper.eq("id", idFilter);
        }
        if (groupIdFilter != null) {
            queryWrapper.like("group_id", groupIdFilter);
        }
        if (groupNameFilter != null) {
            queryWrapper.eq("group_name", groupNameFilter);
        }
        if (groupOwnerFilter != null) {
            queryWrapper.eq("group_owner", groupOwnerFilter);
        }
        return tbGroupService.list(queryWrapper);
    }
    /**
     * 通过id查询群组
     */
    @PostMapping("/listByid")
    public List<TbGroup> listById(@RequestBody Map<String, Object> requestParams){
        return tbGroupService.listByMap(requestParams);
    }
    /**
     * 通过群组id查询群成员
     */
    @PostMapping("/byIdListAllUser")
    public List<TbUser> byIdListUser(@RequestBody Map<String, Object> requestParams){
        List<TbGroup> tbGroupList=tbGroupService.listByMap(requestParams);
        TbGroup tbGroup = tbGroupList.isEmpty()? null : tbGroupList.get(0);
        HashMap<String, Object> paramMap = new HashMap<>();
        assert tbGroup != null;
        paramMap.put("group_id",tbGroup.getId());
        return tbUserService.listByMap(paramMap);
    }
    /**
     * 群主转让
     */
    @PostMapping("/transfer")
    public boolean transferGroup(@RequestBody Map<String, Object> requestParams){
        String name = (String) requestParams.get("name");
        String groupId = (String) requestParams.get("groupId");
        Map<String,Object> groupMap = new HashMap<>();
        groupMap.put("id", groupId);
        List<TbGroup> tbGroupList= tbGroupService.listByMap(groupMap);
        for (TbGroup tbgroup : tbGroupList) {
            tbgroup.setGroupOwner(name);
            return tbGroupService.updateById(tbgroup);
        }
        return false;
    }
    @PostMapping("/mod")
    public boolean mod(@RequestBody TbGroup tbGroup){
        return tbGroupService.updateById(tbGroup);
    }

    /**
     * 创建群组
     */
    @PostMapping("/createGroup")
    public TbGroup createGroup(@RequestBody TbGroup tbGroup){
        tbGroupService.save(tbGroup);
        TbGroup tb =tbGroup;
        Map<String, Object> map = new HashMap<>();
        map.put("group_id",tb.getGroupId());
        map.put("group_name",tb.getGroupName());
        map.put("group_owner",tb.getGroupOwner());
        List<TbGroup> tbGroupList=tbGroupService.listByMap(map);
        TbGroup tbGroup1 = tbGroupList.isEmpty()? null : tbGroupList.get(0);
        return tbGroup1;
    }
    @PostMapping("/transfergrouppoint")
    public Result transferGroupPoint(@RequestBody TransferGroupPointRequest requestParams) {
        String groupId = requestParams.getGroupId();
        Integer groupIdInt = Integer.valueOf(groupId);
        Integer groupPoint = 0;

        for (TransferGroupPointRequest.UserIdPoint userParam : requestParams.getUserIds()) {
            String openid = userParam.getOpenid();
            Integer point = userParam.getPoint();

            Map<String, Object> userMap = new HashMap<>();
            userMap.put("openid", openid);
            List<TbUser> tbUserList = tbUserService.listByMap(userMap);
            TbUser user = tbUserList.isEmpty()? null : tbUserList.get(0);

            if (user == null) {
                continue; // 处理用户不存在的情况
            }

            assert user!= null;
            Integer userPoint = user.getPointbalance();
            TbPointtransactionrecords pointReduce = new TbPointtransactionrecords();

            if (userPoint < point) {
                continue;
            } else {
                Integer userid = user.getId();
                user.setPointbalance(userPoint - point);
                pointReduce.setUserid(userid);
                pointReduce.setIncomeOrExpenseType(-1);
                pointReduce.setPointValue(point);
                pointReduce.setUserName(user.getName());

                groupPoint += point;
            }
            if(!tbUserService.updateById(user) ||!tbPointtransactionrecordsService.save(pointReduce)){
                return Result.fail("用户扣积分失败");
            }
        }

        Map<String, Object> groupMap = new HashMap<>();
        groupMap.put("id", groupIdInt);
        List<TbGroup> tbGroupList = tbGroupService.listByMap(groupMap);
        TbGroup group = tbGroupList.isEmpty()? null : tbGroupList.get(0);

        assert group!= null;
        Integer originalGroupPoint = group.getGroupPoint();
        group.setGroupPoint(originalGroupPoint + groupPoint);

        TbGroupPointFlow tbGroupPointFlow = new TbGroupPointFlow();
        tbGroupPointFlow.setGroupid(Integer.valueOf(groupId));
        tbGroupPointFlow.setIncomeOrExpenseType(1);
        tbGroupPointFlow.setGroupName(group.getGroupName());
        tbGroupPointFlow.setPointValue(groupPoint);

        if (tbGroupService.updateById(group) && tbGroupPointFlowService.save(tbGroupPointFlow)) {
            return success("积分已转至群组");
        } else {
            return fail("积分转群组失败");
        }
    }

//    @PostMapping("/transfergrouppoint")
//    public Result transferGroupPoint(@RequestBody Map<String, Object> requestParams){
//        String openid = (String) requestParams.get("openid");
//        Integer point = (Integer) requestParams.get("point");
//        String groupidStr = (String) requestParams.get("groupid");
//        Integer groupid = Integer.valueOf(groupidStr);
//        TbGroupPointFlow tbGroupPointFlow = new TbGroupPointFlow();
//        Map<String,Object> groupMap = new HashMap<>();
//        groupMap.put("groupid",groupid);
//        List<TbGroup> tbGroupList = tbGroupService.listByMap(groupMap);
//        TbGroup group = tbGroupList.isEmpty()? null : tbGroupList.get(0);
//        assert group != null;
//        Integer grouppoint = group.getGroupPoint();
//        Map<String,Object> userMap = new HashMap<>();
//        userMap.put("openid",openid);
//        List<TbUser> tbUserList=tbUserService.listByMap(userMap);
//        TbUser user = tbUserList.isEmpty()? null : tbUserList.get(0);
//        assert user != null;
//        Integer userpoint = user.getPointbalance();
//        TbPointtransactionrecords pointreduce = new TbPointtransactionrecords();
//        if (userpoint < point) {
//            return pointNotEnough();
//        } else {
//            Integer userid = user.getId();
//            user.setPointbalance(userpoint - point);
//            pointreduce.setUserid(userid);
//            pointreduce.setIncomeOrExpenseType(-1);
//            pointreduce.setPointValue(point);
//            pointreduce.setUserName(user.getName());
//            group.setGroupPoint(grouppoint + point);
//            tbGroupPointFlow.setGroupid(groupid);
//            tbGroupPointFlow.setIncomeOrExpenseType(1);
//            tbGroupPointFlow.setGroupName(group.getGroupName());
//            tbGroupPointFlow.setPointValue(point);
//            tbGroupPointFlow.setOpenid(openid);
//            tbGroupPointFlow.setUsername(user.getName());
//            if (tbUserService.updateById(user) && tbPointtransactionrecordsService.save(pointreduce)&&tbGroupService.updateById(group)&&tbGroupPointFlowService.save(tbGroupPointFlow)) {
//                return success("积分已转至群组");
//            } else {
//                return fail("积分转群组失败");
//            }
//        }
//    }
}

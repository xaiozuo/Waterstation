package com.waterstation.waterstation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.waterstation.waterstation.common.Result;
import com.waterstation.waterstation.entity.TbAdmin;
import com.waterstation.waterstation.entity.TbGroup;
import com.waterstation.waterstation.entity.TbUser;
import com.waterstation.waterstation.service.TbGroupService;
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
    public Result transferGroupPoint(@RequestBody Map<String, Object> requestParams){

//        String userIdsStr = (String) requestParams.get("user_ids"); // 获取多个用户 id 字符串
//        String[] userIds = userIdsStr.split(","); // 分割得到单个用户 id 数组
//        String groupId = (String) requestParams.get("groupId");
//        for (String userId : userIds) {
//            Map<String, Object> userMap = new HashMap<>();
//            userMap.put("openid", userId);
//            List<TbUser> tbUserList = tbUserService.listByMap(userMap);
//            TbUser user = tbUserList.isEmpty()? null : tbUserList.get(0);
//            if (user == null) {
//                continue; // 处理用户不存在的情况
//            }
//            assert user!= null;
//            Integer point = user.getPointbalance();
//            // 以下是针对每个用户的处理逻辑
        Integer openid = (Integer) requestParams.get("openid");
        Integer point = (Integer) requestParams.get("point");
        String groupId = (String) requestParams.get("groupId");
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("openid",openid);
        List<TbUser> tbUserList=tbUserService.listByMap(userMap);
        TbUser user = tbUserList.isEmpty()? null : tbUserList.get(0);
        assert user != null;
        Integer point1 = user.getPointbalance();
        if (point1 < point) {
            return pointNotEnough();
        } else {
            Integer userid = user.getId();
            user.setPointbalance(point1 - point);

            if (tbUserService.updateById(user)) {
                return success("扣除积分成功");
            } else {
                return fail("扣除积分失败");
            }
//            Map<String, Object> groupMap = new HashMap<>();
//            groupMap.put("id", groupId);
//            List<TbGroup> tbGroupList = tbGroupService.listByMap(groupMap);
//
//            return Result.success("群组积分转换成功");
        }
    }
}

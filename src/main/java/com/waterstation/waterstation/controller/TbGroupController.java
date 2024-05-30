package com.waterstation.waterstation.controller;

import com.waterstation.waterstation.entity.TbGroup;
import com.waterstation.waterstation.entity.TbUser;
import com.waterstation.waterstation.service.TbGroupService;
import com.waterstation.waterstation.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}

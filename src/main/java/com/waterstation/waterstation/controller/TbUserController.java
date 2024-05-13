package com.waterstation.waterstation.controller;


import com.waterstation.waterstation.entity.TbUser;
import com.waterstation.waterstation.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjj
 * @since 2024-05-10
 */
@RestController
@RequestMapping("/tb-user")
public class TbUserController {

    @Autowired
    private TbUserService tbUserService;


    @GetMapping("/list")
    public List<TbUser> list(){return tbUserService.list();}

    @PostMapping("/listByOpenid")
    public List<TbUser> listByid(@RequestBody Map<String, Object> requestParams){
        return tbUserService.listByMap(requestParams);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody TbUser tbUser){
        return tbUserService.save(tbUser);
    }

//    @PostMapping("/incrementPointBalance")
//    public boolean incrementPointBalance(@RequestBody Map<String, Object> requestParams) {
//        Integer id = (Integer) requestParams.get("id");
//        Integer type = (Integer) requestParams.get("type");
//        if (type == 1) {
//            TbUser user = tbUserService.getById(id);
//            user.setId(id);
//            user.setPointbalance(user.getPointbalance() + 100);
//            return tbUserService.updateById(user);
//        }else{
//            TbUser user = tbUserService.getById(id);
//            user.setId(id);
//            user.setPointbalance(user.getPointbalance() - 100);
//            return tbUserService.updateById(user);
//        }
//    }

    @PostMapping("/mod")
    public boolean mod(@RequestBody TbUser tbUser){
        return tbUserService.updateById(tbUser);
    }

    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody TbUser tbUser){
        return tbUserService.saveOrUpdate(tbUser);
    }

    @GetMapping("/delete")
    public boolean delete(Integer id){
        return tbUserService.removeById(id);
    }

}

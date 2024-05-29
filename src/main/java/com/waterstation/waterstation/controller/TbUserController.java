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
    public List<TbUser> listBy(@RequestBody Map<String, Object> requestParams){
        return tbUserService.listByMap(requestParams);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody TbUser tbUser){
        return tbUserService.save(tbUser);
    }

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

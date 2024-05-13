package com.waterstation.waterstation.controller;


import com.waterstation.waterstation.entity.TbAdmin;
import com.waterstation.waterstation.service.TbAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjj
 * @since 2024-05-10
 */
@RestController
@Controller
@RequestMapping("/tb-admin")
public class TbAdminController {

    @Autowired
    private TbAdminService tbAdminService;

    @GetMapping("/list")
    public List<TbAdmin> list(){
        return tbAdminService.list();
    }

    //新增
    @PostMapping("/save")
    public boolean save(@RequestBody TbAdmin tbAdmin){
        return tbAdminService.save(tbAdmin);
    }

    @PostMapping("/mod")
    public boolean mod(@RequestBody TbAdmin tbAdmin){
        return tbAdminService.updateById(tbAdmin);
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody TbAdmin tbAdmin){
        return tbAdminService.saveOrUpdate(tbAdmin);
    }

    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return tbAdminService.removeById(id);
    }

}

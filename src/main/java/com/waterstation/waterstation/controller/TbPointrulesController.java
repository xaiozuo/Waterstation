package com.waterstation.waterstation.controller;


import com.waterstation.waterstation.entity.TbPointrules;
import com.waterstation.waterstation.service.TbPointrulesService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/tb-pointrules")
public class TbPointrulesController {

    @Autowired
    private TbPointrulesService tbPointrulesService;

    @GetMapping("/list")
    public List<TbPointrules> list(){return tbPointrulesService.list();}

    @PostMapping("/save")
    public boolean save(@RequestBody TbPointrules tbPointrules){
        return tbPointrulesService.save(tbPointrules);
    }

    @PostMapping("/mod")
    public boolean mod(@RequestBody TbPointrules tbPointrules){
        return tbPointrulesService.updateById(tbPointrules);
    }

    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody TbPointrules tbPointrules){
        return tbPointrulesService.saveOrUpdate(tbPointrules);
    }

    @GetMapping("/delete")
    public boolean delete(Integer id){
        return tbPointrulesService.removeById(id);
    }

}

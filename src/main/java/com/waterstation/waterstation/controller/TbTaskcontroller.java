package com.waterstation.waterstation.controller;

import com.waterstation.waterstation.entity.TbTask;
import com.waterstation.waterstation.service.TbTaskService;
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
@RequestMapping("/tb-task")
public class TbTaskcontroller {
    @Autowired
    private TbTaskService tbTaskService;

    @GetMapping("/list")
    public List<TbTask> list(){return tbTaskService.list();}

    @PostMapping("/save")
    public boolean save(@RequestBody TbTask tbTask){

        return tbTaskService.save(tbTask);
    }
    @PostMapping("/mod")
    public boolean mod(@RequestBody TbTask tbTask){
        return tbTaskService.updateById(tbTask);
    }

    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody TbTask tbTask){
        return tbTaskService.saveOrUpdate(tbTask);
    }

    @GetMapping("/delete")
    public boolean delete(Integer id){
        return tbTaskService.removeById(id);
    }
}

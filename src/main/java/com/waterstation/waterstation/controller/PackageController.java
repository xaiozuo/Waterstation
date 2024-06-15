package com.waterstation.waterstation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.waterstation.waterstation.entity.Package;
import com.waterstation.waterstation.entity.TbAdmin;
import com.waterstation.waterstation.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/package")
public class PackageController {
    @Autowired
    private PackageService packageService;

//    @GetMapping("/list")
//    public List<Package> list(){return packageService.list();}
    @GetMapping("/list")
    public List<Package> list(
            Integer idFilter,
            Double waterAmountFilter,
            Integer pointsFilter,
            Integer statusFilter
            ) {
        QueryWrapper<Package> queryWrapper = new QueryWrapper<>();
        if (idFilter != null) {
            queryWrapper.eq("id", idFilter);
        }
        if (waterAmountFilter != null) {
            queryWrapper.like("water_amount", waterAmountFilter);
        }
        if (pointsFilter != null) {
            queryWrapper.eq("points", pointsFilter);
        }
        if (statusFilter != null) {
            queryWrapper.eq("status", statusFilter);
        }

        return packageService.list(queryWrapper);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Package pg){
        return packageService.save(pg);
    }

    @PostMapping("/mod")
    public boolean mod(@RequestBody Package pg){
        return packageService.updateById(pg);
    }

    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Package pg){
        return packageService.saveOrUpdate(pg);
    }

    @GetMapping("/delete")
    public boolean delete(Integer id){
        return packageService.removeById(id);
    }
}

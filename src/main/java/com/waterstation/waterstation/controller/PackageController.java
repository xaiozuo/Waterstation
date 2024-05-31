package com.waterstation.waterstation.controller;

import com.waterstation.waterstation.entity.Package;
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

    @GetMapping("/list")
    public List<Package> list(){return packageService.list();}

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

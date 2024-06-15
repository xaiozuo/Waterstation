package com.waterstation.waterstation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.waterstation.waterstation.common.Result;
import com.waterstation.waterstation.entity.TbAdmin;
import com.waterstation.waterstation.entity.TbPointrules;
import com.waterstation.waterstation.service.TbPointrulesService;
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
/**
 * 积分规则的增删改查
 */
@RestController
@RequestMapping("/tb-pointrules")
public class TbPointrulesController {

    @Autowired
    private TbPointrulesService tbPointrulesService;

//    @GetMapping("/list")
//    public List<TbPointrules> list(){return tbPointrulesService.list();}
    @GetMapping("/list")
    public List<TbPointrules> list(
            Integer idFilter,
            String rulesnameFilter,
            Integer countsFilter,
            Integer increasepointFilter,
            Integer reducePointFilter,
            Integer exchangeicFilter,
            Integer exchangeWaterFilter) {
        QueryWrapper<TbPointrules> queryWrapper = new QueryWrapper<>();
        if (idFilter != null) {
            queryWrapper.eq("id", idFilter);
        }
        if (rulesnameFilter != null) {
            queryWrapper.like("rulesname", rulesnameFilter);
        }
        if (countsFilter != null) {
            queryWrapper.eq("counts", countsFilter);
        }
        if (increasepointFilter != null) {
            queryWrapper.eq("increasepoint", increasepointFilter);
        }
        if (reducePointFilter != null) {
            queryWrapper.eq("reduce_point", reducePointFilter);
        }
        if (exchangeWaterFilter != null) {
            queryWrapper.eq("exchange_water", exchangeWaterFilter);
        }
        if (exchangeicFilter != null) {
            queryWrapper.eq("exchangeic", exchangeicFilter);
        }
        return tbPointrulesService.list(queryWrapper);
    }
    @PostMapping("/ic")
    public Result modicpoint(@RequestBody Map<String, Object> requestParams){
        Integer point = (Integer) requestParams.get("point");
        TbPointrules tbPointrules = tbPointrulesService.getById(1);
        tbPointrules.setExchangeic(point);
        if(tbPointrulesService.updateById(tbPointrules)){
            return Result.success("修改积分兑换ic零钱成功,每元所要积分为：" + point,point);
        }else{
            return Result.fail("修改失败");
        }
    }
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

package com.waterstation.waterstation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.waterstation.waterstation.entity.TbPointrules;
import com.waterstation.waterstation.entity.TbPointtransactionrecords;
import com.waterstation.waterstation.entity.TbUser;
import com.waterstation.waterstation.service.TbPointtransactionrecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@RequestMapping("/tb-pointtransactionrecords")
public class TbPointtransactionrecordsController {

    @Autowired
    private TbPointtransactionrecordsService tbPointtransactionrecordsService;

//    @GetMapping("/list")
//    public List<TbPointtransactionrecords> list(){return tbPointtransactionrecordsService.list();}
    @GetMapping("/list")
    public List<TbPointtransactionrecords> list(
            Integer idFilter,
            String userNameFilter,
            Integer userIdFilter,
            Integer incomeOrExpenseTypeFilter,
            Integer pointValueFilter,
            String pointChangeTypeFilter,
            Integer outletFilter,
            String deviceIdFilter,
            String advertisementLinkFilter,
            LocalDateTime transactionTimeFilter) {
        QueryWrapper<TbPointtransactionrecords> queryWrapper = new QueryWrapper<>();
        if (idFilter != null) {
            queryWrapper.eq("id", idFilter);
        }
        if (userNameFilter != null) {
            queryWrapper.like("user_name", userNameFilter);
        }
        if (userIdFilter != null) {
            queryWrapper.eq("user_id", userIdFilter);
        }
        if (incomeOrExpenseTypeFilter != null) {
            queryWrapper.eq("income_or_expense_type", incomeOrExpenseTypeFilter);
        }
        if (pointValueFilter != null) {
            queryWrapper.eq("point_value", pointValueFilter);
        }
        if (pointChangeTypeFilter != null) {
            queryWrapper.eq("point_change_type", pointChangeTypeFilter);
        }
        if (deviceIdFilter != null) {
            queryWrapper.eq("device_id", deviceIdFilter);
        }
        if (outletFilter != null) {
            queryWrapper.eq("outlet", outletFilter);
        }
        if (advertisementLinkFilter != null) {
            queryWrapper.eq("advertisement_link", advertisementLinkFilter);
        }
        if (transactionTimeFilter != null) {
            queryWrapper.eq("transaction_time", transactionTimeFilter);
        }
        return tbPointtransactionrecordsService.list(queryWrapper);
    }

    @PostMapping("/listByOpenid")
    public List<TbPointtransactionrecords> listByid(@RequestBody Map<String, Object> requestParams){
        return tbPointtransactionrecordsService.listByMap(requestParams);
    }
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return tbPointtransactionrecordsService.removeById(id);
    }

//    // 搜索功能
//    @GetMapping("/search")
//    public List<TbPointtransactionrecords> search(@RequestParam("query") String query) {
//        return tbPointtransactionrecordsService.search(query);
//    }
//
//    // 详情功能
//    @GetMapping("/detail/{id}")
//    public TbPointtransactionrecords detail(@PathVariable("id") Integer id) {
//        return tbPointtransactionrecordsService.getById(id);
//    }
//
//    // 分页功能
//    @GetMapping("/page")
//    public Page<TbPointtransactionrecords> page(@RequestParam("page") int page, @RequestParam("size") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return tbPointtransactionrecordsService.page(pageable);
//    }
}

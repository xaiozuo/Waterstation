package com.waterstation.waterstation.controller;


import com.waterstation.waterstation.entity.TbPointtransactionrecords;
import com.waterstation.waterstation.entity.TbUser;
import com.waterstation.waterstation.service.TbPointtransactionrecordsService;
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
@RequestMapping("/tb-pointtransactionrecords")
public class TbPointtransactionrecordsController {

    @Autowired
    private TbPointtransactionrecordsService tbPointtransactionrecordsService;

    @GetMapping("/list")
    public List<TbPointtransactionrecords> list(){return tbPointtransactionrecordsService.list();}

    @PostMapping("/listByOpenid")
    public List<TbPointtransactionrecords> listByid(@RequestBody Map<String, Object> requestParams){
        return tbPointtransactionrecordsService.listByMap(requestParams);
    }
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return tbPointtransactionrecordsService.removeById(id);
    }
}

package com.waterstation.waterstation.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.waterstation.waterstation.entity.TbGroup;
import com.waterstation.waterstation.mapper.TbGroupMapper;
import com.waterstation.waterstation.service.TbGroupService;
import org.springframework.stereotype.Service;

@Service
public class TbGroupServiceImpl extends ServiceImpl<TbGroupMapper, TbGroup> implements TbGroupService {
}
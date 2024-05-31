package com.waterstation.waterstation.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.waterstation.waterstation.entity.Package;
import com.waterstation.waterstation.mapper.PackageMapper;
import com.waterstation.waterstation.service.PackageService;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class PackageServiceImpl extends ServiceImpl<PackageMapper, Package> implements PackageService {
}

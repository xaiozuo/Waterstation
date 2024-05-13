package com.waterstation.waterstation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waterstation.waterstation.entity.TbUser;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjj
 * @since 2024-05-10
 */
public interface TbUserMapper extends BaseMapper<TbUser> {
    TbUser selectByOpenid(String openid);
}

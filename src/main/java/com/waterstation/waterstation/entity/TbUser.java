package com.waterstation.waterstation.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zjj
 * @since 2024-05-10
 */
/**
 * 用户实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbUser对象", description="")
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 小程序获取的openid
     */
    private String openid;

    /**
     * 用户头像
     */
    private String profilePhoto;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户积分余额
     */
    private Integer pointbalance;

    /**
     * 用户已看广告次数
     */
    private Integer taskCount;

    /**
     * 用户权限
     */
    private Integer role;

    /**
     * 用户所在群组
     */
    private Integer groupId;

}

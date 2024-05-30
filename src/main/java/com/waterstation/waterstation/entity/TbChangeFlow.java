package com.waterstation.waterstation.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * （订单）零钱流水实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ChangeFlow", description="")
public class TbChangeFlow implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 创建订单时间
     */
    private LocalDateTime createTime;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 是否支付
     */
    private boolean isPay;

    /**
     * 微信订单号
     */
    private String wxOrder;

    /**
     * 用户id
     */
    private Integer userid;

}

package com.waterstation.waterstation.entity;

import java.math.BigDecimal;
import java.io.Serializable;
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
 * 积分规则实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbPointrules对象", description="")
public class TbPointrules implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 规则名称
     */
    private String rulesname;

    /**
     * 每天看广告上限
     */
    private Integer counts;

    /**
     * 单次看广告加的积分数
     */
    private Integer increasepoint;

    /**
     * 积分对换水的积分数
     */
    private Integer reducePoint;

    /**
     * 积分兑换水的水量
     */
    private Integer exchangeWater;
}

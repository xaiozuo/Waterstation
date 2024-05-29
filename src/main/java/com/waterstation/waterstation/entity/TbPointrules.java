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
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbPointrules对象", description="")
public class TbPointrules implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String rulesname;

    private Integer counts;

    private Integer increasepoint;

    private Integer reducePoint;

    private Integer exchangeWater;
}

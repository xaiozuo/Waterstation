package com.waterstation.waterstation.entity;

import java.io.Serial;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 管理员实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbAdmin对象", description="")
public class Package implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 套餐水量
     */
    private double waterAmount;

    /**
     * 套餐花费积分
     */
    private Integer points;

    /**
     * 套餐上线状态（1上线，-1未上线）
     */
    private Integer status;
}

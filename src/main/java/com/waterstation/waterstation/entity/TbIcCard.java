package com.waterstation.waterstation.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ic卡实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbIcCard对象", description="")
public class TbIcCard implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户openid
     */
    private String openid;

    /**
     * ic卡id
     */
    private String icid;
}

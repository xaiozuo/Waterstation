package com.waterstation.waterstation.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Administrator
 */

/**
 * 群组实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbGroup对象", description="")
public class TbGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 群组id
     */
    private String groupId;

    /**
     * 群主名称
     */
    private String groupName;

    /**
     * 群主（openid）
     */
    private String groupOwner;

    /**
     * 组内积分
     */

    private Integer groupPoint;

}

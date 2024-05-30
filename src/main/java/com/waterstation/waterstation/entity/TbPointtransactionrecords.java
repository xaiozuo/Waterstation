package com.waterstation.waterstation.entity;

import java.time.LocalDateTime;
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
 * 积分流水实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbPointtransactionrecords对象", description="")
public class TbPointtransactionrecords implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userid;

    /**
     * 用户姓名
     */
    private  String userName;

    /**
     * 记录的类型（1为增加，-1减少）
     */
    private Integer incomeOrExpenseType;

    /**
     * 积分值
     */
    private Integer pointValue;

    /**
     * 积分变动类型（adv，water）
     */
    private String pointChangeType;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 出水口
     */
    private Integer outlet;

    /**
     * 广告链接
     */
    private String advertisementLink;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;

}

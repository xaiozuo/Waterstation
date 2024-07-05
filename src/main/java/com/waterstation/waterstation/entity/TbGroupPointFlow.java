package com.waterstation.waterstation.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbGroupPointFlow对象", description="")
public class TbGroupPointFlow {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 群组id
     */
    private Integer groupid;

    /**
     * 群组名称
     */
    private  String groupName;

    /**
     * 记录的类型（1为增加，-1减少）
     */
    private Integer incomeOrExpenseType;

    /**
     * 积分值
     */
    private Integer pointValue;

    /**
     * 用户openid
     */
    private String openid;

    /**
     * 用户姓名
     */
    private String username;

    /**
     * 交易时间
     */
    private LocalDateTime transactionTime;
}

package com.waterstation.waterstation.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ChangeFlow", description="")
public class TbChangeFlow implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String orderSn;

    private BigDecimal price;

    private LocalDateTime createTime;

    private LocalDateTime payTime;

    private boolean isPay;

    private String wxOrder;

    private Integer userid;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getPayTime() {
        return payTime;
    }

    public void setPayTime(LocalDateTime payTime) {
        this.payTime = payTime;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public String getWxOrder() {
        return wxOrder;
    }

    public void setWxOrder(String wxOrder) {
        this.wxOrder = wxOrder;
    }
}

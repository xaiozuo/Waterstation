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
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbPointtransactionrecords对象", description="")
public class TbPointtransactionrecords implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userid;

    private  String userName;

    private Integer incomeOrExpenseType;

    private Integer pointValue;

    private String pointChangeType;

    private String deviceId;

    private Integer outlet;

    private String advertisementLink;

    private LocalDateTime transactionTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIncomeOrExpenseType() {
        return incomeOrExpenseType;
    }

    public void setIncomeOrExpenseType(Integer incomeOrExpenseType) {
        this.incomeOrExpenseType = incomeOrExpenseType;
    }

    public Integer getPointValue() {
        return pointValue;
    }

    public void setPointValue(Integer pointValue) {
        this.pointValue = pointValue;
    }

    public String getPointChangeType() {
        return pointChangeType;
    }

    public void setPointChangeType(String pointChangeType) {
        this.pointChangeType = pointChangeType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getOutlet() {
        return outlet;
    }

    public void setOutlet(Integer outlet) {
        this.outlet = outlet;
    }

    public String getAdvertisementLink() {
        return advertisementLink;
    }

    public void setAdvertisementLink(String advertisementLink) {
        this.advertisementLink = advertisementLink;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}

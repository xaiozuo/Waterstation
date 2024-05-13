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
@ApiModel(value="TbQrcode对象", description="")
public class TbQrcode implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String saler;

    private String appid;

    private BigDecimal outlet;

    private String chaddress;

    private String imgname;

    private String codeaddress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaler() {
        return saler;
    }

    public void setSaler(String saler) {
        this.saler = saler;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public BigDecimal getOutlet() {
        return outlet;
    }

    public void setOutlet(BigDecimal outlet) {
        this.outlet = outlet;
    }

    public String getChaddress() {
        return chaddress;
    }

    public void setChaddress(String chaddress) {
        this.chaddress = chaddress;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public String getCodeaddress() {
        return codeaddress;
    }

    public void setCodeaddress(String codeaddress) {
        this.codeaddress = codeaddress;
    }
}

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
 * 设备二维码实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbQrcode对象", description="")
public class TbQrcode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 商户
     */
    private String saler;

    /**
     * appid
     */
    private String appid;

    /**
     * 出水口
     */
    private BigDecimal outlet;

    /**
     * 中文地址
     */
    private String chaddress;

    /**
     * 二维码图片名称
     */
    private String imgname;

    /**
     * 二维码所在地址
     */
    private String codeaddress;

    /**
     * *设备名称
     */
    private String deviceName;

}

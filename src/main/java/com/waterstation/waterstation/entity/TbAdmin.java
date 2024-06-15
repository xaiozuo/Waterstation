package com.waterstation.waterstation.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.waterstation.waterstation.service.TbAdminService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author zjj
 * @since 2024-05-10
 */
/**
 * 管理员实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbAdmin对象", description="")
public class TbAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer Id;

    /**
     * 管理员姓名
     */
    private String adminName;

    /**
     * 管理员密码
     */
    private String adminPassword;

    /**
     * 管理员权限
     */
    private String permission;

    /**
     * 添加时间
     */
    private LocalDateTime addTime;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 性别
     */
    private String gender;
}

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
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbAdmin对象", description="")
public class TbAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer Id;

    private String adminName;

    private String adminPassword;

    private String permission;

    private LocalDateTime addTime;



}

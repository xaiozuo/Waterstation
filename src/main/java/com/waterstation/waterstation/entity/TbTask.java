package com.waterstation.waterstation.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value="TbTask对象", description="")
public class TbTask implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer taskcounts;

    private LocalDateTime createtime;

}
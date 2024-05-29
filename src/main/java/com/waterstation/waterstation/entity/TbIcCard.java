package com.waterstation.waterstation.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbIcCard对象", description="")
public class TbIcCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String openid;

    private String icid;
}

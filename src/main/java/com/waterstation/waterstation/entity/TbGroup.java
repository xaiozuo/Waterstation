package com.waterstation.waterstation.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TbGroup对象", description="")
public class TbGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String groupId;

    private String groupName;

    private String groupOwner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(String groupOwner) {
        this.groupOwner = groupOwner;
    }
}

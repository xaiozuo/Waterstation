package com.waterstation.waterstation.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="TbUser对象", description="")
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String openid;

    private String profilePhoto;

    private String phone;

    private String name;

    private Integer pointbalance;

    private Integer taskCount;

    private Integer role;

    private Integer groupId;

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPointbalance() {
        return pointbalance;
    }

    public void setPointbalance(Integer pointbalance) {
        this.pointbalance = pointbalance;
    }

    public Integer getRole() {return role;}

    public void setRole(Integer role) {this.role = role;}

    public Integer getGroupId() {return groupId;}

    public void setGroupId(Integer groupId) {this.groupId = groupId;}
}

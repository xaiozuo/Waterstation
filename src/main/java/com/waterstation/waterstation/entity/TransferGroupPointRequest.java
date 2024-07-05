package com.waterstation.waterstation.entity;

import java.util.List;

public class TransferGroupPointRequest {
    private List<UserIdPoint> userIds;
    private String groupId;
    // 为 UserIdPoint 类添加 getter 和 setter 方法

    public List<UserIdPoint> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<UserIdPoint> userIds) {
        this.userIds = userIds;
    }


    public static class UserIdPoint {
        private String openid;
        private Integer point;

        // 为 openid 和 point 添加 getter 和 setter 方法

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public Integer getPoint() {
            return point;
        }

        public void setPoint(Integer point) {
            this.point = point;
        }
    }

    // 为 groupId 添加 getter 和 setter 方法

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

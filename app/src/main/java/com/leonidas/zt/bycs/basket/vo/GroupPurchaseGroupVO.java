package com.leonidas.zt.bycs.basket.vo;

import java.util.List;

/**
 * Created by 华强 on 2018/4/1.
 * Version: V1.0
 * Description:
 * Others: 暂无
 * ReviseHistory(Author、Date、RevisePart)： 暂无
 */

public class GroupPurchaseGroupVO {

    /**
     * code : 1
     * hint : 成功！
     * data : [{"groupId":"1516524865509895578","leadMemberId":"1516524865547172642","groupNum":5,"groupCount":1,"groupStatus":0,"deadDate":1516464000000,"activeStatus":0,"createTime":1516524866000,"updateTime":1516524866000,"groupMemberList":[{"memberId":"1516524865547172642","groupId":"1516524865509895578","userId":1,"userName":"aaa","userHeadPath":"head1.jpg","orderId":"1516409765791624065"}]},{"groupId":"1516525125256135108","leadMemberId":"1516525250696553840","groupNum":5,"groupCount":1,"groupStatus":0,"deadDate":1516464000000,"activeStatus":0,"createTime":1516525251000,"updateTime":1516525251000,"groupMemberList":[{"memberId":"1516525250696553840","groupId":"1516525125256135108","userId":1,"userName":"aaa","userHeadPath":"head1.jpg","orderId":"1516409765791624065"}]},{"groupId":"1516525266185358755","leadMemberId":"1516525540812301319","groupNum":5,"groupCount":1,"groupStatus":0,"deadDate":1516464000000,"activeStatus":0,"createTime":1516525541000,"updateTime":1516525541000,"groupMemberList":[{"memberId":"1516525540812301319","groupId":"1516525266185358755","userId":1,"userName":"aaa","userHeadPath":"head1.jpg","orderId":"1516409765791624065"}]},{"groupId":"1516525579859535525","leadMemberId":"1516525596103836801","groupNum":5,"groupCount":1,"groupStatus":0,"deadDate":1516464000000,"activeStatus":0,"createTime":1516525596000,"updateTime":1516525596000,"groupMemberList":[{"memberId":"1516525596103836801","groupId":"1516525579859535525","userId":1,"userName":"aaa","userHeadPath":"head1.jpg","orderId":"1516409765791624065"}]}]
     */

    private int code;
    private String hint;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * groupId : 1516524865509895578
         * leadMemberId : 1516524865547172642
         * groupNum : 5
         * groupCount : 1
         * groupStatus : 0
         * deadDate : 1516464000000
         * activeStatus : 0
         * createTime : 1516524866000
         * updateTime : 1516524866000
         * groupMemberList : [{"memberId":"1516524865547172642","groupId":"1516524865509895578","userId":1,"userName":"aaa","userHeadPath":"head1.jpg","orderId":"1516409765791624065"}]
         */

        private String groupId;
        private String leadMemberId;
        private int groupNum;
        private int groupCount;
        private int groupStatus;
        private long deadDate;
        private int activeStatus;
        private long createTime;
        private long updateTime;
        private List<GroupMemberListBean> groupMemberList;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getLeadMemberId() {
            return leadMemberId;
        }

        public void setLeadMemberId(String leadMemberId) {
            this.leadMemberId = leadMemberId;
        }

        public int getGroupNum() {
            return groupNum;
        }

        public void setGroupNum(int groupNum) {
            this.groupNum = groupNum;
        }

        public int getGroupCount() {
            return groupCount;
        }

        public void setGroupCount(int groupCount) {
            this.groupCount = groupCount;
        }

        public int getGroupStatus() {
            return groupStatus;
        }

        public void setGroupStatus(int groupStatus) {
            this.groupStatus = groupStatus;
        }

        public long getDeadDate() {
            return deadDate;
        }

        public void setDeadDate(long deadDate) {
            this.deadDate = deadDate;
        }

        public int getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(int activeStatus) {
            this.activeStatus = activeStatus;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public List<GroupMemberListBean> getGroupMemberList() {
            return groupMemberList;
        }

        public void setGroupMemberList(List<GroupMemberListBean> groupMemberList) {
            this.groupMemberList = groupMemberList;
        }

        public static class GroupMemberListBean {
            /**
             * memberId : 1516524865547172642
             * groupId : 1516524865509895578
             * userId : 1
             * userName : aaa
             * userHeadPath : head1.jpg
             * orderId : 1516409765791624065
             */

            private String memberId;
            private String groupId;
            private int userId;
            private String userName;
            private String userHeadPath;
            private String orderId;

            public String getMemberId() {
                return memberId;
            }

            public void setMemberId(String memberId) {
                this.memberId = memberId;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserHeadPath() {
                return userHeadPath;
            }

            public void setUserHeadPath(String userHeadPath) {
                this.userHeadPath = userHeadPath;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }
        }

    }
}

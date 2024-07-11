package com.example.LeaveApplicationPortal.DTO;

import org.springframework.data.annotation.Id;

public class LeaveDTO {

    @Id
    private String _id;
    private String userid;
    private String username;
    private String leaveType;
    private String startDate;
    private String endDate;
    private int count;
    private String msg;
    private String approver;
    private String status;

    public LeaveDTO(String _id, String userid, String username, String leaveType, String startDate, String endDate, int count, String msg, String approver, String status) {
        this._id = _id;
        this.userid = userid;
        this.username = username;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.count = count;
        this.msg = msg;
        this.approver = approver;
        this.status = status;
    }

    public LeaveDTO() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LeaveDto{" +
                "_id='" + _id + '\'' +
                ", userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", leaveType='" + leaveType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", count=" + count +
                ", msg='" + msg + '\'' +
                ", approver='" + approver + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

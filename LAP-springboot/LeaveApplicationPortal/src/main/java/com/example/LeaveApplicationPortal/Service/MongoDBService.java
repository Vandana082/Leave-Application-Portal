/*package com.example.LeaveApplicationPortal.Service;

import com.example.LeaveApplicationPortal.DBOperations.HolidayDBOperations;
import com.example.LeaveApplicationPortal.DBOperations.LeaveDBOperations;
import com.example.LeaveApplicationPortal.DBOperations.LeaveRequestDBOperations;
import com.example.LeaveApplicationPortal.DBOperations.UserDBOperations;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;


@Service
public class MongoDBService {

    @Autowired
    private UserDBOperations userDBOperations;

    @Autowired
    private LeaveDBOperations leaveDBOperations;

    @Autowired
    private LeaveRequestDBOperations leaveRequestDBOperations;

    @Autowired
    private HolidayDBOperations holidayDBOperations;

    //Saving form details
    public LoginResponse saveUser(Map<String, String> formData1) {
        DBObject formData = new BasicDBObject();
        final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        boolean isValidEmail = pattern.matcher(formData1.get("email")).matches();
        if (formData1.get("username") == null || formData1.get("username").isEmpty() || formData1.get("userid") == null || formData1.get("userid").isEmpty() || formData1.get("email") == null || formData1.get("email").isEmpty() || formData1.get("password") == null || formData1.get("password").isEmpty() || formData1.get("category") == null || formData1.get("category").isEmpty()) {
            return new LoginResponse("All fields are mandatory", false);
        }
        if(userDBOperations.findByUserid(formData1.get("userid"))) {
            return new LoginResponse("User already exists", false);
        }
        if (!isValidEmail) {
            return new LoginResponse("Please enter valid email address", false);
        }
        if (formData1.get("password").length() < 6) {
            return new LoginResponse("Password should contain at least 6 characters", false);
        }
        if (formData1.get("password").length() > 12) {
            return new LoginResponse("Password cannot exceed more than 12 characters", false);
        }
        else {
            formData.putAll(formData1);
            userDBOperations.save(formData);
            return new LoginResponse("Sign up successful", true);
        }
    }


    //Login validation
    public LoginResponse loginUser(Map<String, String> loginData) {
        if(loginData.get("userid") == null || loginData.get("userid").isEmpty() || loginData.get("password") == null || loginData.get("password").isEmpty()) {
            return new LoginResponse("All fields are mandatory", false);
        }
        if (userDBOperations.findOneByUseridAndPassword(loginData.get("userid"), loginData.get("password"))) {
            if(userDBOperations.findByCategory(loginData.get("userid"))) {
                return new LoginResponse("Login as Management", true);
            }
            else {
                return new LoginResponse("Login as Employee", true);
            }
        }
        else {
            return new LoginResponse("Password incorrect", false);
        }
    }

    //Get document by userid
    public Document get(String userid) {
        return userDBOperations.findUser(userid);
    }

    //Change password
    public LoginResponse changePassword(String userid, String currentPwd, String newPwd) {
        if(userDBOperations.findOneByUseridAndPassword(userid, currentPwd)) {
            userDBOperations.changePassword(userid, newPwd);
            return new LoginResponse("Password changed", true);
        }
        else {
            return new LoginResponse("Wrong password", false);
        }
    }

    //forgot password
    public LoginResponse forgotPassword(String userid, String email) {
        if(userDBOperations.findOneByUseridAndEmail(userid, email)) {
            return new LoginResponse("userid found", true);
        }
        else {
            return new LoginResponse("userid not found", false);
        }
    }

    //change password when forgot
    public LoginResponse changePwd(String userid, String password) {
        userDBOperations.changePassword(userid, password);
        return new LoginResponse("Password changed", true);
    }

    //saving leave details
    public LoginResponse saveLeave(Map<String, String> formData1, MultipartFile file) throws IOException {
            if (formData1.get("leaveType") == null || formData1.get("leaveType").isEmpty() || formData1.get("startDate") == null || formData1.get("startDate").isEmpty() || formData1.get("endDate") == null || formData1.get("endDate").isEmpty() || formData1.get("msg") == null || formData1.get("msg").isEmpty() || formData1.get("approver") == null || formData1.get("approver").isEmpty()) {
                return new LoginResponse("All fields are mandatory", false);
            }
            if (leaveRequestDBOperations.findByUserid(formData1.get("userid"))) {
                if (file == null || file.isEmpty()) {
                    DBObject formData = new BasicDBObject();
                    int count = Integer.parseInt(formData1.get("count"));
                    formData.putAll(formData1);
                    formData.put("count", count);
                    formData.put("file", null);
                    leaveDBOperations.save(formData);
                    leaveRequestDBOperations.save(formData);
                }
                else {
                    DBObject formData = new BasicDBObject();
                    byte[] imageData = file.getBytes();
                    Binary imageBinary = new Binary(imageData);
                    int count = Integer.parseInt(formData1.get("count"));
                    formData.putAll(formData1);
                    formData.put("count", count);
                    formData.put("file", imageBinary);
                    leaveDBOperations.save(formData);
                    leaveRequestDBOperations.save(formData);
                }
                return new LoginResponse("Leave applied", true);
            }
        else {
            return new LoginResponse("Your leave request is in pending status", false);
        }
    }

    //get usernames of management dept
    public List<String> getUsernamesByCategoryManagement() {
        return userDBOperations.findUsernameByCategoryManagement();
    }

    //get all leave records
    public List<Document> getLeaveRecords(String status) {
        if(Objects.equals(status, "All")) {
            return leaveDBOperations.findAll();
        }
        else {
            return leaveDBOperations.findByStatus(status);
        }
    }

    //get leave records according to userid
    public List<Document> getEmpLeave(String userid, String status) {
        if(Objects.equals(status, "All")) {
            return leaveDBOperations.findLeaveByUserid(userid);
        }
        else {
            return leaveDBOperations.findLeaveByUseridAndStatus(userid, status);
        }
    }

    //get leave request records according to approver
    public List<Document> getLeaveRequestRecords(String approver) {
        return leaveRequestDBOperations.findByApprover(approver);
    }

    //update the status of leave request
    public LoginResponse updateLeaveRequest(String userid, String newStatus) {
        return leaveRequestDBOperations.updateStatus(userid, newStatus);
    }

    //count the number of leaves according to leave type
    public int sumIntValues(String userid, String leaveType) {
        return leaveDBOperations.findByUseridAndLeaveType(userid, leaveType);
    }

    //get all holiday records
    public List<Document> getHolidayRecords() {
        return holidayDBOperations.findAll();
    }

    //get all user details
    public List<Document> getUser() {
        return userDBOperations.findAll();
    }

    public LoginResponse deleteUser(String userid) {
        userDBOperations.deleteUser(userid);
        return new LoginResponse("User deleted", true);
    }

    public ResponseEntity<byte[]> getFile(String userid) {
        return leaveRequestDBOperations.getFile(userid);
    }

    public List<Document> editLeave(String userid) {
        return leaveRequestDBOperations.findByUser(userid);
    }

    public LoginResponse updateLeaveForm(Map<String, String> formData, MultipartFile file) throws IOException {
        if (formData.get("leaveType") == null || formData.get("leaveType").isEmpty() || formData.get("startDate") == null || formData.get("startDate").isEmpty() || formData.get("endDate") == null || formData.get("endDate").isEmpty() || formData.get("msg") == null || formData.get("msg").isEmpty() || formData.get("approver") == null || formData.get("approver").isEmpty()) {
            return new LoginResponse("All fields are mandatory", false);
        }
        if (!leaveRequestDBOperations.findByUserid(formData.get("userid"))) {
            int count = Integer.parseInt(formData.get("count"));
            if (file == null || file.isEmpty()) {
                DBObject formData1 = new BasicDBObject();
                formData1.putAll(formData);
                formData1.put("count", count);
                formData1.put("file", null);
                leaveRequestDBOperations.update(formData1);
            }
            else {
                DBObject formData2 = new BasicDBObject();
                byte[] imageData = file.getBytes();
                Binary imageBinary = new Binary(imageData);
                formData2.putAll(formData);
                formData2.put("count", count);
                formData2.put("file", imageBinary);
                leaveRequestDBOperations.update(formData2);
            }
            return new LoginResponse("Updated", true);
        }
        else {
            return new LoginResponse("Leave request not found", false);
        }
    }
}*/

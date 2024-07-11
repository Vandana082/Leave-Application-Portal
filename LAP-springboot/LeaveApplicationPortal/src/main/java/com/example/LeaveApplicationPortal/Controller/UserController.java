package com.example.LeaveApplicationPortal.Controller;

import com.example.LeaveApplicationPortal.DTO.LeaveDTO;
import com.example.LeaveApplicationPortal.DTO.LoginDTO;
import com.example.LeaveApplicationPortal.DTO.UserDTO;
import com.example.LeaveApplicationPortal.DTO.UsernameDTO;
import com.example.LeaveApplicationPortal.Entity.Holidays;
import com.example.LeaveApplicationPortal.Entity.Leave;
import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.example.LeaveApplicationPortal.Service.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UserController {


    @Autowired
    private Services services;


    //Operations on User details

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveUser(UserDTO userDto) {
        LoginResponse loginResponse = services.saveUser(userDto);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(LoginDTO loginDto) {
        LoginResponse loginResponse = services.loginUser(loginDto);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(value = "/logout")
    public void logout() {
        SecurityContextHolder.clearContext();
    }


    @RequestMapping(value = "/get/{userid}")
    public User get(@PathVariable(name = "userid") String userid) {
        return services.get(userid);
    }


    @PutMapping("/changePassword/{userid}/{current}/{new}")
    public ResponseEntity<?> changePassword(@PathVariable("userid")String userid, @PathVariable("current")String currentPwd, @PathVariable("new")String newPwd) {
        LoginResponse loginResponse = services.changePassword(userid, currentPwd, newPwd);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/forgotPassword/{userid}/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable("userid")String userid, @PathVariable("email")String email) {
        LoginResponse loginResponse = services.forgotPassword(userid, email);
        return ResponseEntity.ok(loginResponse);
    }

    @PutMapping("/changePwd/{userid}/{password}")
    public ResponseEntity<?> changePwd(@PathVariable("userid")String userid, @PathVariable("password")String password) {
    LoginResponse loginResponse = services.changePwd(userid, password);
    return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/getUser")
    public List<User> getUser() {
        return services.getUser();
    }

    @DeleteMapping("/deleteUser/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable("userid")String userid) {
        LoginResponse loginResponse = services.deleteUser(userid);
        return ResponseEntity.ok(loginResponse);
    }




    //Operations on leave records

    @PostMapping(value = "/saveLeave")
    public ResponseEntity<?> saveLeave(LeaveDTO leaveDto, MultipartFile file) {
        LoginResponse loginResponse = services.saveLeave(leaveDto, file);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/management/usernames/{username}")
    public List<UsernameDTO> getUsernamesInManagementCategory(@PathVariable("username")String username) {
        return services.getUsernamesByCategoryManagement(username);
    }

    @GetMapping("/leaveRecords")
    public List<Leave> getLeaveRecords(@RequestParam String status, @RequestParam(defaultValue = "") String search) {
        return services.getLeaveRecords(status, search);
    }

    @GetMapping("/empLeave/{userid}")
    public List<Leave> getEmpLeave(@PathVariable("userid") String userid, @RequestParam String status) {
        return services.getEmpLeave(userid, status);
    }

    @GetMapping("/leaveRequestRecords/{username}")
    public List<Leave> getLeaveRequestRecords(@PathVariable("username") String approver) {
        return services.getLeaveRequestRecords(approver);
    }

    @PutMapping("/status/{userid}/{newStatus}")
    public ResponseEntity<?> updateLeaveRequest(@PathVariable("userid") String userid, @PathVariable("newStatus") String newStatus) {
            LoginResponse loginResponse = services.updateLeaveRequest(userid, newStatus);
            return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/sum/{userid}/{leaveType}")
    public int sumIntValues(@PathVariable("userid")String userid, @PathVariable("leaveType")String leaveType) {
        return services.sumIntValues(userid, leaveType);
    }

    @GetMapping("/file/{id}")
    public byte[] getFile(@PathVariable("id")String id) {
        return services.getFile(id);
    }


    @GetMapping("/editLeave/{userid}")
    public List<Leave> editLeave(@PathVariable("userid")String userid) {
        return services.editLeave(userid);
    }

    @PutMapping("/updateLeave")
    public LoginResponse updateLeave(@RequestParam Map<String, String> formData, MultipartFile file) {
        return services.updateLeaveForm(formData, file);
    }

    @DeleteMapping("/deleteLeave/{_id}")
    public ResponseEntity<?> deleteLeave(@PathVariable("_id") String _id) {
        LoginResponse loginResponse = services.deleteLeave(_id);
        return ResponseEntity.ok(loginResponse);
    }


    //Calender events
    @GetMapping("/holidays")
    public List<Holidays> getHolidayRecords() {
        return services.getHolidayRecords();
    }


}

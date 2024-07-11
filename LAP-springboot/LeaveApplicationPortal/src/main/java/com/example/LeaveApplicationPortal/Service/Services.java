package com.example.LeaveApplicationPortal.Service;

import com.example.LeaveApplicationPortal.DTO.LeaveDTO;
import com.example.LeaveApplicationPortal.DTO.LoginDTO;
import com.example.LeaveApplicationPortal.DTO.UserDTO;
import com.example.LeaveApplicationPortal.DTO.UsernameDTO;
import com.example.LeaveApplicationPortal.Entity.Holidays;
import com.example.LeaveApplicationPortal.Entity.Leave;
import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface Services {
    LoginResponse saveUser(UserDTO userDto);

    LoginResponse loginUser(LoginDTO loginDto);

    User get(String userid);

    LoginResponse changePassword(String userid, String currentPwd, String newPwd);

    LoginResponse forgotPassword(String userid, String email);

    LoginResponse changePwd(String userid, String password);

    List<User> getUser();

    LoginResponse deleteUser(String userid);

    LoginResponse saveLeave(LeaveDTO leaveDto, MultipartFile file);

    List<UsernameDTO> getUsernamesByCategoryManagement(String username);

    List<Leave> getLeaveRecords(String status, String search);

    List<Leave> getEmpLeave(String userid, String status);

    List<Leave> getLeaveRequestRecords(String approver);

    LoginResponse updateLeaveRequest(String userid, String newStatus);

    int sumIntValues(String userid, String leaveType);

    LoginResponse updateLeaveForm(Map<String, String> formData, MultipartFile file);

    byte[] getFile(String id);

    List<Leave> editLeave(String userid);

    List<Holidays> getHolidayRecords();

    LoginResponse deleteLeave(String _id);


}

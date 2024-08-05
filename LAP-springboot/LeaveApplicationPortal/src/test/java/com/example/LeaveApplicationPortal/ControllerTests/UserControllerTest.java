package com.example.LeaveApplicationPortal.ControllerTests;

import com.example.LeaveApplicationPortal.Controller.UserController;
import com.example.LeaveApplicationPortal.DTO.LeaveDTO;
import com.example.LeaveApplicationPortal.DTO.LoginDTO;
import com.example.LeaveApplicationPortal.DTO.UserDTO;
import com.example.LeaveApplicationPortal.DTO.UsernameDTO;
import com.example.LeaveApplicationPortal.Entity.Holidays;
import com.example.LeaveApplicationPortal.Entity.Leave;
import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@WithMockUser(username = "Vandana", roles = "USER")
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ServiceImpl service;

    private User user;

    private Leave leave1;
    private Leave leave2;

    @BeforeEach
    public void setUp() {
        user = new User("abc", "001", "abc@gmail.com", "xyz", "password", "Management");
        leave1 = new Leave("669f77ed13973306005bd76d", "001", "abc", "Earned_Leave", "23/07/2024", "24/07/2024", 2, "Please approve my leave", "Rohith", "Pending", null);
        leave2 = new Leave("669f77ed13973306005bd77e", "002", "efg", "Earned_Leave", "23/07/2024", "23/07/2024", 1, "Please approve my leave", "Akshatha", "Pending", null);
    }

    @Test
    public void testSaveUser() throws Exception {
        UserDTO userDTO = new UserDTO("abc", "001", "abc@gmail.com", "xyz", "password", "Management");
        when(service.saveUser(userDTO)).thenReturn(new LoginResponse("Sign up successful", true));
        mockMvc.perform(post("/api/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testLoginUser() throws Exception {
        LoginDTO loginDTO = new LoginDTO("001", "password");
        when(service.loginUser(loginDTO)).thenReturn(new LoginResponse("Login as Management", true));
        mockMvc.perform(post("/api/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testGet() throws Exception {
        when(service.get("001")).thenReturn(user);
        mockMvc.perform(get("/api/get/001")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userid").value("001"))
                .andExpect(jsonPath("$.username").value("abc"));
    }

    @Test
    public void testChangePassword() throws Exception {
        when(service.changePassword("001", "password", "newPassword")).thenReturn(new LoginResponse("Password changed", true));
        mockMvc.perform(put("/api/changePassword/001/password/newPassword")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password changed"));
    }

    @Test
    public void testForgotPassword() throws Exception {
        when(service.forgotPassword("001", "abc@gmail.com")).thenReturn(new LoginResponse("userid found", true));
        mockMvc.perform(post("/api/forgotPassword/001/abc@gmail.com")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("userid found"));
    }

    @Test
    public void testChangePwd() throws Exception {
        when(service.changePwd("001", "newPassword")).thenReturn(new LoginResponse("Password changed", true));
        mockMvc.perform(put("/api/changePwd/001/newPassword")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password changed"));
    }

    @Test
    public void testGetUser() throws Exception {
        User user1 = new User("efg", "002", "efg@gmail.com", "xyz", "password12", "Employee");
        List<User> userList = List.of(user, user1);
        when(service.getUser()).thenReturn(userList);
        mockMvc.perform(get("/api/getUser")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userid").value("001"))
                .andExpect(jsonPath("$[1].userid").value("002"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(service.deleteUser("001")).thenReturn(new LoginResponse("deleted successfully", true));
        mockMvc.perform(delete("/api/deleteUser/001")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("deleted successfully"));
    }

    @Test
    public void testSaveLeave() throws Exception {
        LeaveDTO leaveDTO = new LeaveDTO("669f77ed13973306005bd76d", "001", "abc", "Earned_Leave", "23/07/2024", "24/07/2024", 2, "Please approve my leave", "Rohith", "Pending", null);
        when(service.saveLeave(leaveDTO, null)).thenReturn(new LoginResponse("Leave applied", true));
        mockMvc.perform(post("/api/saveLeave")
                .with(csrf())
                .content(objectMapper.writeValueAsString(leaveDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUsernamesInManagementCategory() throws Exception {
        UsernameDTO usernameDTO1 = new UsernameDTO("user1");
        UsernameDTO usernameDTO2 = new UsernameDTO("user2");
        UsernameDTO usernameDTO3 = new UsernameDTO("user3");
        List<UsernameDTO> usernameDTOList = List.of(usernameDTO2, usernameDTO3);
        when(service.getUsernamesByCategoryManagement("user1")).thenReturn(usernameDTOList);
        mockMvc.perform(get("/api/management/usernames/user1")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user2"));
    }

    @Test
    public void testGetLeaveRecords() throws Exception {
        List<Leave> leaveList = List.of(leave1, leave2);
        when(service.getLeaveRecords("All", "")).thenReturn(leaveList);
        mockMvc.perform(get("/api/leaveRecords")
                        .param("status", "All")
                        .param("search", "")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]._id").value("669f77ed13973306005bd76d"))
                .andExpect(jsonPath("$[1]._id").value("669f77ed13973306005bd77e"));
    }

    @Test
    public void testGetEmpLeave() throws Exception {
        List<Leave> leaveList = List.of(leave2);
        when(service.getEmpLeave("002", "Pending")).thenReturn(leaveList);
        mockMvc.perform(get("/api/empLeave/002")
                        .param("status", "Pending")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userid").value("002"))
                .andExpect(jsonPath("$[0].status").value("Pending"));
    }

    @Test
    public void testGetLeaveRequestRecords() throws Exception {
        List<Leave> leaveList = List.of(leave1);
        when(service.getLeaveRequestRecords("Rohith")).thenReturn(leaveList);
        mockMvc.perform(get("/api/leaveRequestRecords/Rohith")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userid").value("001"))
                .andExpect(jsonPath("$[0].approver").value("Rohith"));
    }

    @Test
    public void testUpdateLeaveRequest() throws Exception {
        when(service.updateLeaveRequest("001", "Accepted")).thenReturn(new LoginResponse("Updated", true));
        mockMvc.perform(put("/api/status/001/Accepted")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Updated"));
    }

    @Test
    public void testSumIntValues() throws Exception {
        int sum = 2;
        when(service.sumIntValues("001", "Earned_Leave")).thenReturn(sum);
        mockMvc.perform(get("/api/sum/001/Earned_Leave")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFile() throws Exception {
        Leave leave3 = new Leave("669f77ed13973306015bd77e", "003", "efg", "Casual_Sick_Leave", "23/07/2024", "24/07/2024", 2, "Please approve my leave", "Akshatha", "Accepted", new byte[0]);
        when(service.getFile("669f77ed13973306015bd77e")).thenReturn(leave3.getFile());
        mockMvc.perform(get("/api/file/669f77ed13973306015bd77e")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testEditLeave() throws Exception {
        List<Leave> leaveList = List.of(leave1);
        when(service.editLeave("001")).thenReturn(leaveList);
        mockMvc.perform(get("/api/editLeave/001")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userid").value("001"));
    }
    
    @Test
    public void testUpdateLeave() throws Exception {
        //Leave leave3 = new Leave("669f77ed13973306015bd77e", "003", "efg", "Earned_Leave", "23/07/2024", "24/07/2024", 2, "Please approve my leave", "Akshatha", "Accepted", null);
        Map<String, String> formData = new HashMap<>();
        MultipartFile file = mock(MultipartFile.class);
        formData.put("_id", "669f77ed13973306005bd76d");
        formData.put("userid", "001");
        formData.put("username", "abc");
        formData.put("leaveType", "Earned_Leave");
        formData.put("startDate", "23/07/2024");
        formData.put("endDate", "24/07/2024");
        formData.put("count", String.valueOf(2));
        formData.put("msg", "Please approve my leave");
        formData.put("approver", "Rohith");
        formData.put("status", "Pending");
        formData.put("file", null);
        when(service.updateLeaveForm(formData, file)).thenReturn(new LoginResponse("Leave updated successfully", true));
        mockMvc.perform(put("/api/updateLeave")
                .param("_id", "669f77ed13973306005bd76d")
                .param("userid", "001")
                .param("username", "abc")
                .param("leaveType", "Earned_Leave")
                .param("startDate", "23/07/2024")
                .param("endDate", "24/07/2024")
                .param("count", String.valueOf(2))
                .param("msg", "Please approve my leave")
                .param("approver", "Rohith")
                .param("status", "Pending")
                .param("file", "new byte[0]")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteLeave() throws Exception {
        when(service.deleteLeave("669f77ed13973306005bd76d")).thenReturn(new LoginResponse("Leave request deleted successfully", true));
        mockMvc.perform(delete("/api/deleteLeave/669f77ed13973306005bd76d")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Leave request deleted successfully"));
    }

    @Test
    public void testGetHolidayRecords() throws Exception {
        Holidays holidays1 = new Holidays("Republic day", "26th January 2024", "Friday");
        Holidays holidays2 = new Holidays("Independence day", "15th August 2024", "Thursday");
        Holidays holidays3 = new Holidays("Gandhi jayanthi", "02nd October 2024", "Wednesday");
        List<Holidays> holidaysList = List.of(holidays1, holidays2, holidays3);
        when(service.getHolidayRecords()).thenReturn(holidaysList);
        mockMvc.perform(get("/api/holidays")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].occassion").value("Republic day"));
    }
}

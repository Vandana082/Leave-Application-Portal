package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.Entity.Leave;
import com.example.LeaveApplicationPortal.Repo.LeaveRepo;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class DeleteLeaveTest {

    @Mock
    LeaveRepo leaveRepo;

    @InjectMocks
    ServiceImpl service;

    @Test
    public void testDeleteLeave() {
        Leave leave1 = new Leave("669e0558e9893f91d6609716", "001", "abc","Earned_Leave", "24/07/2024", "24/07/2024", 1, "Please approve my leave", "Akshatha", "Accepted", null);
        LoginResponse loginResponse = service.deleteLeave(leave1.get_id());
        assertNotNull(loginResponse);
        assertTrue(loginResponse.getStatus());
        assertEquals("Leave request deleted successfully", loginResponse.getMessage());
        verify(leaveRepo, times(1)).deleteBy_id(leave1.get_id());
    }
}

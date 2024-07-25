package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.Entity.Leave;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UpdateLeaveFormTest {

    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    ServiceImpl service;

    Map<String, String> leave1 = new HashMap<>();
    Map<String, String> leave2 = new HashMap<>();
    MultipartFile file = mock(MultipartFile.class);

    @BeforeEach
    void setUp() throws IOException {
        leave1.put("_id", "669e0558e9893f91d6609716");
        leave1.put("userid", "001");
        leave1.put("username", "abc");
        leave1.put("leaveType", "Earned_Leave");
        leave1.put("startDate", "24/07/2024");
        leave1.put("endDate", "24/07/2024");
        leave1.put("count", String.valueOf(1));
        leave1.put("msg", "Please approve my leave");
        leave1.put("approver", "Akshatha");
        leave1.put("status", "Pending");
        leave1.put("file", null);

        leave2.put("_id", "669e0558e9893f91d6609716");
        leave2.put("userid", "001");
        leave2.put("username", "abc");
        leave2.put("leaveType", "Casual_Sick_Leave");
        leave2.put("startDate", "24/07/2024");
        leave2.put("endDate", "25/07/2024");
        leave2.put("count", String.valueOf(2));
        leave2.put("msg", "Please approve my newLeave");
        leave2.put("approver", "Akshatha");
        leave2.put("status", "Pending");
        leave2.put("file", Arrays.toString(new byte[0]));
    }

    @Test
    public void testUpdateLeaveForm() throws IOException {
        Query expectedQuery = service.createUpdateQuery("001");
        Update expectedUpdate = new Update().set("leaveType", leave1.get("leaveType")).set("startDate", leave1.get("startDate")).set("endDate", leave1.get("endDate")).set("count", leave1.get("count")).set("msg", leave1.get("msg")).set("approver", leave1.get("approver")).set("file", leave1.get("file"));
        LoginResponse loginResponse = service.updateLeaveForm(leave1, file);
        assertTrue(loginResponse.getStatus());
        assertEquals("Leave updated successfully", loginResponse.getMessage());
        verify(mongoTemplate, times(1)).updateFirst(expectedQuery, expectedUpdate, Leave.class);
    }

    @Test
    public void testUpdateLeaveForm_Error() throws IOException {
        when(file.getBytes()).thenThrow(new IOException());
        Query expectedQuery = service.createUpdateQuery("001");
        Update expectedUpdate = new Update().set("leaveType", leave2.get("leaveType")).set("startDate", leave2.get("startDate")).set("endDate", leave2.get("endDate")).set("count", leave2.get("count")).set("msg", leave2.get("msg")).set("approver", leave2.get("approver")).set("file", leave2.get("file").getBytes());
        LoginResponse loginResponse = service.updateLeaveForm(leave2, file);
        assertFalse(loginResponse.getStatus());
        assertEquals("Error occurred while processing file", loginResponse.getMessage());
        verify(mongoTemplate, never()).updateFirst(expectedQuery, expectedUpdate, Leave.class);
    }
}

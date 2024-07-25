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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UpdateLeaveRequestTest {

    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    ServiceImpl service;

    private Leave leave1;
    private Leave leave2;

    MultipartFile file = mock(MultipartFile.class);

    @BeforeEach
    void setUp() throws IOException {
        leave1 = new Leave("669e0558e9893f91d6609716", "001", "abc","Earned_Leave", "24/07/2024", "24/07/2024", 1, "Please approve my leave", "Akshatha", "Pending", null);
        leave2 = new Leave("669e0558e9893f91d6609717", "002", "efg","Earned_Leave", "24/07/2024", "25/07/2024", 2, "Please approve my leave", "Rohith", "Declined", null);
    }

    @Test
    public void testCreateUpdateQuery() {
        Query expectedQuery = new Query(Criteria.where("userid").is(leave1.getUserid())).addCriteria(Criteria.where("status").is("Pending"));
        Query actualQuery = service.createUpdateQuery("001");
        assertNotNull(actualQuery);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testUpdateLeaveRequest() {
        Query expectedQuery = new Query(Criteria.where("userid").is(leave1.getUserid())).addCriteria(Criteria.where("status").is(leave1.getStatus()));
        String newStatus = "Accepted";
        LoginResponse loginResponse = service.updateLeaveRequest(leave1.getUserid(), newStatus);
        Update expectedUpdate = new Update().set("status", newStatus);
        assertTrue(loginResponse.getStatus());
        assertEquals("Updated", loginResponse.getMessage());
        verify(mongoTemplate, times(1)).updateFirst(expectedQuery, expectedUpdate, Leave.class);
    }
}

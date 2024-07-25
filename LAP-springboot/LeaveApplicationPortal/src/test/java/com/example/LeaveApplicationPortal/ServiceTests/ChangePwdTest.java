package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Repo.UserRepo;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ChangePwdTest {

    @Mock
    MongoTemplate mongoTemplate;

    @Mock
    UserRepo userRepo;

    @InjectMocks
    ServiceImpl service;

    private User user;
    String newPwd = "newPassword";

    @BeforeEach
    public void setUp() {
        user = new User("abc", "001", "abc@gmail.com", "xyz", "password", "Management");
    }

    @Test
    public void testChangePwd() {
        Query expectedQuery = new Query(Criteria.where("userid").is(user.getUserid()));
        Update expectedUpdate = new Update().set("password", newPwd);
        user.setPassword(newPwd);
        LoginResponse loginResponse = service.changePwd("001", newPwd);
        assertNotNull(loginResponse);
        assertTrue(loginResponse.getStatus());
        assertEquals("Password changed", loginResponse.getMessage());
        assertEquals(newPwd, user.getPassword());
        verify(mongoTemplate, times(1)).updateFirst(expectedQuery, expectedUpdate, User.class);
    }
}

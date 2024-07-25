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
import static org.mockito.Mockito.*;

@SpringBootTest
public class UpdatePasswordTest {

    @Mock
    MongoTemplate mongoTemplate;
    @Mock
    UserRepo userRepo;

    @InjectMocks
    ServiceImpl service;

    private User user;

    String newPassword = "newPassword";

    @BeforeEach
    public void setUp() {
        user = new User("abc", "001", "abc@gmail.com", "xyz", "password", "Management");
    }

    @Test
    public void testUpdatePassword_WrongPassword() {
        Query expectedQuery = new Query(Criteria.where("userid").is(user.getUserid())).addCriteria(Criteria.where("password").is("wrongPassword"));
        Update expectedUpdate = new Update().set("password", newPassword);
        when(userRepo.findByUserid(user.getUserid())).thenReturn(user);
        LoginResponse loginResponse = service.changePassword("001", "wrongPassword", newPassword);
        assertNotNull(loginResponse);
        assertFalse(loginResponse.getStatus());
        assertEquals("Wrong password", loginResponse.getMessage());
        verify(mongoTemplate).updateFirst(expectedQuery, expectedUpdate, User.class);
        verify(userRepo, times(1)).findByUserid(user.getUserid());
    }

    @Test
    public void testUpdatePassword_Changed() {
        Query expectedQuery = new Query(Criteria.where("userid").is(user.getUserid())).addCriteria(Criteria.where("password").is(user.getPassword()));
        Update expectedUpdate = new Update().set("password", newPassword);
        user.setPassword(newPassword);
        when(userRepo.findByUserid(user.getUserid())).thenReturn(user);
        LoginResponse loginResponse = service.changePassword("001", "password", newPassword);
        assertNotNull(loginResponse);
        assertTrue(loginResponse.getStatus());
        assertEquals("Password changed", loginResponse.getMessage());
        assertEquals(newPassword, user.getPassword());
        verify(mongoTemplate, times(1)).updateFirst(expectedQuery, expectedUpdate, User.class);
        verify(userRepo, times(1)).findByUserid(user.getUserid());
    }
}

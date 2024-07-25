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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ForgotPasswordTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    ServiceImpl service;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("abc", "001", "abc@gmail.com", "xyz", "password", "Management");
    }

    @Test
    public void testForgotPassword_UserNotFound() {
        when(userRepo.findByUseridAndEmail(user.getUserid(), "Wrong Email")).thenReturn(null);
        LoginResponse loginResponse = service.forgotPassword("001", "Wrong Email");
        assertNotNull(loginResponse);
        assertFalse(loginResponse.getStatus());
        assertEquals("userid not found", loginResponse.getMessage());
        verify(userRepo, times(1)).findByUseridAndEmail(user.getUserid(), "Wrong Email");
    }

    @Test
    public void testForgotPassword_UserFound() {
        when(userRepo.findByUseridAndEmail(user.getUserid(), user.getEmail())).thenReturn(user);
        LoginResponse loginResponse = service.forgotPassword("001", "abc@gmail.com");
        assertNotNull(loginResponse);
        assertTrue(loginResponse.getStatus());
        assertEquals("userid found", loginResponse.getMessage());
        verify(userRepo, times(1)).findByUseridAndEmail(user.getUserid(), user.getEmail());
    }
}

package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginResponseTest {

    @Autowired
    private ServiceImpl service;

    @Test
    public void testCreateLoginResponse_Success() {
        String message = "Success";
        boolean status = true;

        LoginResponse loginResponse = service.createLoginResponse(message, status);
        assertNotNull(loginResponse);
        assertEquals(message, loginResponse.getMessage());
        assertTrue(loginResponse.getStatus());
    }

    @Test
    public void testCreateLoginResponse_Failure() {
        String message = "Failure";
        boolean status = false;

        LoginResponse loginResponse = service.createLoginResponse(message, status);
        assertNotNull(loginResponse);
        assertEquals(message, loginResponse.getMessage());
        assertFalse(loginResponse.getStatus());
    }
}

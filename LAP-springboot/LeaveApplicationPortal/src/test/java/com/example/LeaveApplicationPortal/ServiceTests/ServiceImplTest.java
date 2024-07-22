package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.DTO.LoginDTO;
import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Repo.UserRepo;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceImplTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    private ServiceImpl service;

    private LoginDTO validLoginDto;
    private LoginDTO invalidLoginDto;

    @BeforeEach
    public void setUp() {
        validLoginDto = new LoginDTO("001", "password");
        invalidLoginDto = new LoginDTO("","");
    }

    @Test
    public void testCreateLoginResponse() {
        String message = "Success";
        boolean status = true;

        LoginResponse loginResponse = service.createLoginResponse(message, status);
        assertNotNull(loginResponse);
        assertEquals(message, loginResponse.getMessage());
        assertEquals(status, loginResponse.getStatus());
    }

    @Test
    public void testLoginUser() {
        //No Login Data
        LoginResponse loginResponse = service.loginUser(invalidLoginDto);
        assertFalse(loginResponse.getStatus());
        assertEquals("All fields are mandatory", loginResponse.getMessage());

        //Password incorrect
        when(userRepo.findOneByUseridAndPassword(validLoginDto.getUserid(), validLoginDto.getPassword())).thenReturn(Optional.empty());
        LoginResponse loginResponse1 = service.loginUser(validLoginDto);
        assertFalse(loginResponse1.getStatus());
        assertEquals("Password incorrect", loginResponse1.getMessage());

        //Login as Management
        User user = new User();
        user.setCategory("Management");

        when(userRepo.findByUserid(validLoginDto.getUserid())).thenReturn(user);
        when(userRepo.findOneByUseridAndPassword(validLoginDto.getUserid(), validLoginDto.getPassword())).thenReturn(Optional.of(user));
        LoginResponse loginResponse2 = service.loginUser(validLoginDto);
        assertTrue(loginResponse2.getStatus());
        assertEquals("Login as Management", loginResponse2.getMessage());

        //Login as Employee
        User user1 = new User();
        user.setCategory("Employee");

        when(userRepo.findByUserid(validLoginDto.getUserid())).thenReturn(user);
        when(userRepo.findOneByUseridAndPassword(validLoginDto.getUserid(), validLoginDto.getPassword())).thenReturn(Optional.of(user));
        LoginResponse loginResponse3 = service.loginUser(validLoginDto);
        assertTrue(loginResponse3.getStatus());
        assertEquals("Login as Employee", loginResponse3.getMessage());
    }
}

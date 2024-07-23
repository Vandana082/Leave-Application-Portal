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
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoginUserTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    private ServiceImpl service;

    private LoginDTO loginDto;

    @BeforeEach
    public void setUp() {
        loginDto = new LoginDTO("001", "password");
    }

    @Test
    public void testFieldsMandatory() {
        loginDto.setUserid("");
        loginDto.setPassword("");
        LoginResponse loginResponse3 = service.loginUser(loginDto);
        assertFalse(loginResponse3.getStatus());
        assertEquals("All fields are mandatory", loginResponse3.getMessage());
    }

    @Test
    public void testPasswordIncorrect() {
        when(userRepo.findOneByUseridAndPassword(loginDto.getUserid(), loginDto.getPassword())).thenReturn(Optional.empty());
        LoginResponse loginResponse2 = service.loginUser(loginDto);
        assertFalse(loginResponse2.getStatus());
        assertEquals("Password incorrect", loginResponse2.getMessage());
    }

    @Test
    public void testLoginAsManagement() {
        User user = new User();
        user.setCategory("Management");

        when(userRepo.findByUserid(loginDto.getUserid())).thenReturn(user);
        when(userRepo.findOneByUseridAndPassword(loginDto.getUserid(), loginDto.getPassword())).thenReturn(Optional.of(user));
        LoginResponse loginResponse = service.loginUser(loginDto);
        assertTrue(loginResponse.getStatus());
        assertEquals("Login as Management", loginResponse.getMessage());
    }

    @Test
    public void testLoginAsEmployee() {
        User user = new User();
        user.setCategory("Employee");

        when(userRepo.findByUserid(loginDto.getUserid())).thenReturn(user);
        when(userRepo.findOneByUseridAndPassword(loginDto.getUserid(), loginDto.getPassword())).thenReturn(Optional.of(user));
        LoginResponse loginResponse5 = service.loginUser(loginDto);
        assertTrue(loginResponse5.getStatus());
        assertEquals("Login as Employee", loginResponse5.getMessage());
    }
}

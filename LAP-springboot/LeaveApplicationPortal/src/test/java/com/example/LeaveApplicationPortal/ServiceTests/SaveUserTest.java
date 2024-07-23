package com.example.LeaveApplicationPortal.ServiceTests;

import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.DTO.UserDTO;
import com.example.LeaveApplicationPortal.Repo.UserRepo;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.example.LeaveApplicationPortal.Service.ServiceIml.ServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
public class SaveUserTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    ServiceImpl service;

    private UserDTO userDto;

    @BeforeEach
    public void setUp() {
        userDto = new UserDTO("abc", "001", "abc@gmail.com", "xyz", "password", "Management");
    }

    @Test
    public void testFieldsMandatory() {
        userDto.setUsername("");
        LoginResponse loginResponse = service.saveUser(userDto);
        assertFalse(loginResponse.getStatus());
        assertEquals("All fields are mandatory", loginResponse.getMessage());
        verify(userRepo, times(0)).save(any(User.class));
    }

    @Test
    public void testUserExists() {
        when(userRepo.findByUserid(any(String.class))).thenReturn(new User());
        LoginResponse loginResponse1 = service.saveUser(userDto);
        assertFalse(loginResponse1.getStatus());
        assertEquals("User ID already exist", loginResponse1.getMessage());
        verify(userRepo, times(0)).save(any(User.class));
    }

    @Test
    public void testInvalidEmail() {
        userDto.setEmail("invalid email");
        LoginResponse loginResponse2 = service.saveUser(userDto);
        assertFalse(loginResponse2.getStatus());
        assertEquals("Please enter valid email address", loginResponse2.getMessage());
        verify(userRepo, times(0)).save(any(User.class));
    }

    @Test
    public void testShortPassword() {
        userDto.setPassword("pass");
        LoginResponse loginResponse3 = service.saveUser(userDto);
        assertFalse(loginResponse3.getStatus());
        assertEquals("Password should contain at least 6 characters", loginResponse3.getMessage());
        verify(userRepo, times(0)).save(any(User.class));
    }

    @Test
    public void testLongPassword() {
        userDto.setPassword("toolongpassword");
        LoginResponse loginResponse4 = service.saveUser(userDto);
        assertFalse(loginResponse4.getStatus());
        assertEquals("Password cannot exceed more than 12 characters", loginResponse4.getMessage());
        verify(userRepo, times(0)).save(any(User.class));
    }

    @Test
    public void testSave() {
        when(userRepo.findByUserid(any(String.class))).thenReturn(null);
        LoginResponse loginResponse5 = service.saveUser(userDto);
        assertTrue(loginResponse5.getStatus());
        assertEquals("Sign up successful", loginResponse5.getMessage());
        verify(userRepo, times(1)).save(any(User.class));
    }
}

package com.example.LeaveApplicationPortal.RepositoryTests;

import com.example.LeaveApplicationPortal.DTO.UsernameDTO;
import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Repo.UserRepo;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

   /*@BeforeAll
    public void setUp() {
        User user = new User();
        user.setUserid("001");
        user.setUsername("ABC");
        user.setEmail("abc@gmail.com");
        user.setRole("xyz");
        user.setPassword("password");
        user.setCategory("employee");
        userRepo.save(user);
    }*/

    @Test
    public void testFindByUserid() {
        User user1 = userRepo.findByUserid("001");
        assertNotNull(user1, "User should not be null");
        assertEquals("001", user1.getUserid(), "User ID should match");
    }

    @Test
    public void testFindOneByUseridAndPassword() {
        Optional<User> user2 = userRepo.findOneByUseridAndPassword("001", "password");
        assertTrue(user2.isPresent(), "User exists");
        assertEquals("001", user2.get().getUserid());
        assertEquals("password", user2.get().getPassword());
        assertFalse(user2.isEmpty(), "User not exists");
    }

    @Test
    public void testFindUsernamesByCategoryManagement() {
        List<UsernameDTO> usernames = userRepo.findUsernamesByCategoryManagement();
        assertThat(usernames).hasSize(3);
        assertThat(usernames).extracting("username").contains("Akshatha", "Rohith", "Akash");
    }

    @Test
    public void testFindByUseridAndEmail() {
        LoginResponse user3 = userRepo.findByUseridAndEmail("001", "abc@gmail.com");
        assertNotNull(user3, "User details are present");
    }


    @Test
    public void testDeleteByUserid() {
        User userBeforeDeletion = userRepo.findByUserid("001");
        assertThat(userBeforeDeletion).isNotNull();
        userRepo.deleteByUserid("001");
        User userAfterDeletion = userRepo.findByUserid("001");
        assertThat(userAfterDeletion).isNull();
    }
}

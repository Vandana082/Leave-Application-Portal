package com.example.LeaveApplicationPortal.RepositoryTests;

import com.example.LeaveApplicationPortal.DTO.UsernameDTO;
import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Repo.UserRepo;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import org.junit.jupiter.api.AfterEach;
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

   @BeforeEach
    public void setUp() {
        User user = new User("Abc", "001", "abc@gmail.com", "xyz", "password", "Employee");
        userRepo.save(user);
    }

    @AfterEach
    public void tearDown() {
       userRepo.deleteByUserid("001");
    }

    @Test
    public void testFindByUserid() {
        //Success
        User user1 = userRepo.findByUserid("001");
        assertNotNull(user1);
        assertThat(user1.getUserid()).isEqualTo("001");
        assertThat(user1.getUsername()).isEqualTo("Abc");

        //Failure
        User user2 = userRepo.findByUserid("110");
        assertNull(user2);
    }

    @Test
    public void testFindOneByUseridAndPassword() {
        //Success
        Optional<User> user3 = userRepo.findOneByUseridAndPassword("001", "password");
        assertThat(user3).isPresent();
        assertEquals("001", user3.get().getUserid());
        assertEquals("password", user3.get().getPassword());

        //Failure
        Optional<User> user4 = userRepo.findOneByUseridAndPassword("001", "password123");
        assertThat(user4).isNotPresent();
    }

    @Test
    public void testFindUsernamesByCategoryManagement() {
        //Success
        List<UsernameDTO> usernames = userRepo.findUsernamesByCategoryManagement();
        assertThat(usernames).hasSize(3);
        assertThat(usernames).extracting("username").contains("Akshatha", "Rohith", "Akash");

        //Failure
        assertFalse(usernames.contains("Abc"));
    }

    @Test
    public void testFindByUseridAndEmail() {
        //Success
        User user5 = userRepo.findByUseridAndEmail("001", "abc@gmail.com");
        assertNotNull(user5);
        assertThat(user5.getUserid()).isEqualTo("001");
        assertThat(user5.getEmail()).isEqualTo("abc@gmail.com");

        //Failure
        User user6 = userRepo.findByUseridAndEmail("110", "abc@gmail.com");
        assertNull(user6);
    }


    @Test
    public void testDeleteByUserid() {
        //Success
        User userBeforeDeletion = userRepo.findByUserid("001");
        assertThat(userBeforeDeletion).isNotNull();

        //Failure
        userRepo.deleteByUserid("001");
        User userAfterDeletion = userRepo.findByUserid("001");
        assertThat(userAfterDeletion).isNull();
    }
}

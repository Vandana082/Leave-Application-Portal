package com.example.LeaveApplicationPortal.Repo;

import com.example.LeaveApplicationPortal.DTO.UsernameDTO;
import com.example.LeaveApplicationPortal.Entity.User;
import com.example.LeaveApplicationPortal.Response.LoginResponse;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String>{
    User findByUserid(String userid);

    Optional<User> findOneByUseridAndPassword(String userid, String password);

    @Aggregation("{ $match :{ category : 'Management' } }")
    List<UsernameDTO> findUsernamesByCategoryManagement();

    User findByUseridAndEmail(String userid, String email);

    void deleteByUserid(String userid);

}

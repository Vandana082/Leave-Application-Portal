package com.example.LeaveApplicationPortal.Repo;

import com.example.LeaveApplicationPortal.Entity.Leave;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


@Repository
public interface LeaveRepo extends MongoRepository<Leave, String>{

    List<Leave> findAll();

    List<Leave> findByStatus(String status);

    List<Leave> findByUserid(String userid);

    List<Leave> findByUseridAndStatus(String userid, String status);

    List<Leave> findByUseridAndLeaveType(String userid, String leaveType);

    List<Leave> findByUseridContainingIgnoreCase(String search);

    List<Leave> findByStatusAndApprover(String pending, String approver);

    void deleteBy_id(String id);

}

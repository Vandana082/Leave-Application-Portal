package com.example.LeaveApplicationPortal.Repo;

import com.example.LeaveApplicationPortal.Entity.Holidays;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface HolidayRepo extends MongoRepository<Holidays, String>{



}

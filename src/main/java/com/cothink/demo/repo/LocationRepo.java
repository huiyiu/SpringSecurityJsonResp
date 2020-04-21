package com.cothink.demo.repo;

import com.cothink.demo.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepo extends JpaRepository<Location,String> {
    List<Location> findByUserIdOrderByUpdateTime(String userId);

}

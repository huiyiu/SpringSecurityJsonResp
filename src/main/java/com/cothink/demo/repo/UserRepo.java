package com.cothink.demo.repo;

import com.cothink.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {
    User findByPhone(String phone);

    User findByCardId(String cardId);
}

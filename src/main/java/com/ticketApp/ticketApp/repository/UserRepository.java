package com.ticketApp.ticketApp.repository;

import com.ticketApp.ticketApp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity getByEmail(String login);

    UserEntity findByUserID(Integer userID);
}

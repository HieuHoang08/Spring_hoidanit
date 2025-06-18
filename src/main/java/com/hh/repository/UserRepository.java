package com.hh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hh.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User hieuhoang);

    List<User> findByEmail(String email);

    User findById(long id);

}

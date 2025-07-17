package com.hh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hh.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User hoidanit); // == UPDATE/ INSERT
    void deleteById(long id); //đây là phương thức , khi gọi sẽ tự động tạo truy vấn sql
    List<User> findOneByEmail(String email);
    List<User> findAll();
    User findById(long id);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}

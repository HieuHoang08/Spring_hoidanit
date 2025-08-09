package com.hh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hh.domain.Cart;
import com.hh.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);

    @Modifying
    @Query(value = "DELETE FROM carts WHERE id = :cartId", nativeQuery = true)
    void deleteCartDetailsByCartId(@Param("cartId") Long cartId);

}

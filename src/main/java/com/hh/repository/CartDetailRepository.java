package com.hh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hh.domain.Cart;
import com.hh.domain.CartDetail;
import com.hh.domain.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    boolean existsByCartAndProduct(Cart cart, Product product); 
    CartDetail findByCartAndProduct(Cart cart, Product product); 
}

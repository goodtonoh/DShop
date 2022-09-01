package com.opus.dapp.repository;

import com.opus.dapp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
    // Cart findByCartIdAndProductId(Long cartId, Long productId);
}

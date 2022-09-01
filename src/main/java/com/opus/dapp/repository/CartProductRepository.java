package com.opus.dapp.repository;

import com.opus.dapp.dto.CartListDto;
import com.opus.dapp.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    CartProduct findByCartIdAndProductId(Long cartId, Long productId);
    List<CartListDto> findByCartId(Long cartId);

}

package com.opus.dapp.controller;

import com.opus.dapp.dto.CartListDto;
import com.opus.dapp.dto.CartOrderDto;
import com.opus.dapp.dto.CartProductDto;
import com.opus.dapp.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니 조회
    @GetMapping("/api/carts")
    public List<CartListDto> getCartList(String username) {
        return cartService.getCartList(username);
    }

    // 장바구니 담기
    @PostMapping("/api/carts")
    public ResponseEntity myCart(@RequestBody CartProductDto cartProductDto, String username) {
        try {
            cartService.addCart(cartProductDto, username);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(HttpStatus.OK);
    }

    // 장바구니 수량 변경
    @PatchMapping("/api/cartProduct/{cartProductId}")
    public ResponseEntity updateCartProduct(@PathVariable Long cartProductId, int quantity) {
        if (quantity <= 0) {
            return new ResponseEntity<String>("최소 수량 1개 이상입니다.", HttpStatus.BAD_REQUEST);
        }
        cartService.updateCartProduct(cartProductId, quantity);
        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
    }

    // 장바구니 삭제
    @DeleteMapping("/api/cartProduct/{cartProductId}")
    public ResponseEntity deleteCartProduct(@PathVariable Long cartProductId) {
        cartService.deleteCartProduct(cartProductId);
        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
    }

    // 장바구니 주문
    @PostMapping("/api/cart/orders")
    public ResponseEntity orders(@RequestBody CartOrderDto cartOrderDto, String username) {

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if (cartOrderDtoList == null || cartOrderDtoList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품을 선택해주세요.", HttpStatus.BAD_REQUEST);
        }
        Long orderId = cartService.orderCartProduct(cartOrderDtoList, username);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}

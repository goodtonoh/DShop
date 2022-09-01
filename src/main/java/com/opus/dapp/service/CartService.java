package com.opus.dapp.service;

import com.opus.dapp.dto.CartListDto;
import com.opus.dapp.dto.CartOrderDto;
import com.opus.dapp.dto.CartProductDto;
import com.opus.dapp.dto.OrderDto;
import com.opus.dapp.model.Cart;
import com.opus.dapp.model.CartProduct;
import com.opus.dapp.model.Product;
import com.opus.dapp.model.User;
import com.opus.dapp.repository.CartProductRepository;
import com.opus.dapp.repository.CartRepository;
import com.opus.dapp.repository.ProductRepository;
import com.opus.dapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;


    // 장바구니 조회
    @Transactional(readOnly = true)
    public List<CartListDto> getCartList (String username) {

        List<CartListDto> cartListDtos = new ArrayList<>();

        User user = userRepository.findByUsername(username);
        Cart cart = cartRepository.findByUserId(user.getId());

        if (cart == null) {
            return cartListDtos;
        }
        cartListDtos = cartProductRepository.findByCartId(cart.getId());

        return cartListDtos;
    }

    // 장바구니 담기
    public Long addCart(CartProductDto cartProductDto, String username) {
        User user = userRepository.findByUsername(username);
        Cart cart = cartRepository.findByUserId(user.getId());

        // 장바구니 생성
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }
        Product product = productRepository.findById(cartProductDto.getProductId())
                .orElseThrow(()-> new IllegalArgumentException("Not Found."));

        CartProduct cartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        // 장바구니에 상품 추가
        if (cartProduct == null) {
            cartProduct = CartProduct.createCartProduct(cart, product, cartProductDto.getQuantity());
            cartProductRepository.save(cartProduct);

        // 장바구니에 해당 상품이 이미 존재한다면 수량 증가
        } else {
            cartProduct.addQuantity(cartProductDto.getQuantity());
        }
        return cartProduct.getId();
    }

    // 장바구니 상품 수량 변경
    public void updateCartProduct(Long cartProductId, int quantity) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(()-> new IllegalArgumentException("Not Found."));
        cartProduct.updateQuantity(quantity);
    }

    // 장바구니 상품 삭제
    public void deleteCartProduct(Long cartProductId) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(()-> new IllegalArgumentException("Not Found."));
        cartProductRepository.delete(cartProduct);
    }

    // 장바구니 주문
    public Long orderCartProduct(List<CartOrderDto> cartOrderDtoList, String username) {

        List<OrderDto> orderDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartProduct cartProduct = cartProductRepository.findById(cartOrderDto.getCartProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Not Found."));
            OrderDto orderDto = new OrderDto();
            orderDto.setProductId(cartProduct.getProduct().getId());
            orderDto.setQuantity(cartProduct.getQuantity());
            orderDtoList.add(orderDto);
        }
        Long orderId = orderService.placeOrder(orderDtoList, username);

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartProduct cartProduct = cartProductRepository.findById(cartOrderDto.getCartProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Not Found."));
            cartProductRepository.delete(cartProduct);
        }
        return orderId;
    }
}

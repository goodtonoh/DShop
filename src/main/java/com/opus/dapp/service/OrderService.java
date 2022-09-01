package com.opus.dapp.service;

import com.opus.dapp.dto.OrderDto;
import com.opus.dapp.model.Order;
import com.opus.dapp.model.OrderProduct;
import com.opus.dapp.model.Product;
import com.opus.dapp.model.User;
import com.opus.dapp.repository.OrderRepository;
import com.opus.dapp.repository.ProductRepository;
import com.opus.dapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    // 장바구니 상품 일괄주문
    public Long placeOrder(List<OrderDto> orderDtoList, String username) {

        // 유저 조회
        User user = userRepository.findByUsername(username);

        List<OrderProduct> orderProductList = new ArrayList<>();
        for (OrderDto orderDto : orderDtoList) {
            Product product = productRepository.findById(orderDto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Not Found."));
            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, orderDto.getQuantity());
            orderProductList.add(orderProduct);
        }

        Order order = Order.createOrder(user, orderProductList);
        orderRepository.save(order);
        return order.getId();

    }
}

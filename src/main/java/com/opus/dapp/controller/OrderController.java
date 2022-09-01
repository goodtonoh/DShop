package com.opus.dapp.controller;

import com.opus.dapp.dto.OrderDto;
import com.opus.dapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/orders")
    public ResponseEntity orders(@RequestBody List<OrderDto> orderDtoList, String username) {
        try {
            Long orderId = orderService.placeOrder(orderDtoList, username);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }
}

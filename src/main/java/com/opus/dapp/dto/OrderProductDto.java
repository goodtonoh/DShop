package com.opus.dapp.dto;

import com.opus.dapp.model.OrderProduct;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductDto {

    private String productName;
    private int quantity;
    private int orderPrice;

    public OrderProductDto(OrderProduct orderProduct, int quantity, int orderPrice) {
        this.productName = orderProduct.getProduct().getProductName();
        this.quantity = orderProduct.getQuantity();
        this.orderPrice = orderProduct.getOrderPrice();
    }
}

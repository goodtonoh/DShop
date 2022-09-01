package com.opus.dapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartListDto {
    private Long cartProductId;
    private String productName;
    private int price;
    private int quantity;

    public CartListDto(Long cartProductId, String productName, int price, int quantity) {
        this.cartProductId = cartProductId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
}

package com.opus.dapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long id;
    private String productName;
    private int price;
    private int stock;
    private double pointRatio;
    private String productShop;

}

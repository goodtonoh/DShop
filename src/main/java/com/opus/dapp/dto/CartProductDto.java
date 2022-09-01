package com.opus.dapp.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductDto {

    @NotNull
    private Long productId;

    @NotNull
    private int quantity;

}

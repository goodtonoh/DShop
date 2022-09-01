package com.opus.dapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart_product")
public class CartProduct extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    public static CartProduct createCartProduct(Cart cart, Product product, int quantity) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);
        return cartProduct;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}

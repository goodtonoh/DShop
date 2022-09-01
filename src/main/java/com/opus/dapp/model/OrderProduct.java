package com.opus.dapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_product")
public class OrderProduct extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    private int orderPrice;
    private int quantity;

    public static OrderProduct createOrderProduct(Product product, int quantity) {

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setQuantity(quantity);
        orderProduct.setOrderPrice(product.getPrice());

        return orderProduct;
    }
    public int getTotalPrice() {
        return this.orderPrice * this.quantity;
    }
}

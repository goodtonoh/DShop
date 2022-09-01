package com.opus.dapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "order")
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public static Order createOrder(User user, List<OrderProduct> orderProductList) {

        Order order = new Order();
        order.setUser(user);
        for (OrderProduct orderProduct : orderProductList) {
            order.addOrderProduct(orderProduct);
        }
        return order;
    }

    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderProduct orderProduct : orderProducts) {
            totalPrice += orderProduct.getTotalPrice();
        }
        return totalPrice;
    }
}

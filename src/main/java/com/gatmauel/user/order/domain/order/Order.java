package com.gatmauel.user.order.domain.order;

import com.gatmauel.user.order.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor  //delete쿼리 할 때 필요한 듯
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String customer;

    @Column(length = 11, nullable = false)
    private String phone;

    @Column(nullable = false)
    private int total;

    @Column(nullable = false)
    private int paid;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String request;

    @Column(length = 100, nullable = false)
    private String address;
}

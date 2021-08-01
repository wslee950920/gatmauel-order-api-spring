package com.gatmauel.user.order.domain.detail;

import com.gatmauel.user.order.domain.food.Food;
import com.gatmauel.user.order.domain.order.Order;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor  //delete쿼리 할 때 필요한 듯
@ToString(exclude = {"food", "order"})
@Getter
@Entity
@Table(name = "details")
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Food food;

    @Column(nullable = false)
    private int num;
}

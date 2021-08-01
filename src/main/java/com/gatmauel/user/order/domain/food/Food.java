package com.gatmauel.user.order.domain.food;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(length=20, nullable=false)
    private String name;

    @Column(nullable=false)
    private int price;
}

package org.myproject.statisticservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table(name = "food")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "calories_100_gramm")
    private BigDecimal calories100Gramm;
    @Column(name = "protein")
    private BigDecimal protein;
    @Column(name = "carbohydrates")
    private BigDecimal carbohydrates;
    @Column(name = "fats")
    private BigDecimal fats;
    @Builder.Default
    @OneToMany(mappedBy = "food")
    private List<FoodRecordFood> foodRecordFoods = new ArrayList<>();
}

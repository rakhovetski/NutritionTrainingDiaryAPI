package org.myproject.statisticservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "food_record_food")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodRecordFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_record_id")
    private FoodRecord foodRecord;

    @Column(name = "amount_100_gramm")
    private Integer amount100Gramm;

    public void setFood(Food food) {
        this.food = food;
        this.food.getFoodRecordFoods().add(this);
    }

    public void setFoodRecord(FoodRecord foodRecord) {
        this.foodRecord = foodRecord;
        this.foodRecord.getFoodRecordFoods().add(this);
    }
}

package org.myproject.statisticservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "food_record")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "record_date")
    private LocalDateTime recordDate;
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Builder.Default
    @OneToMany(mappedBy = "foodRecord")
    private List<FoodRecordFood> foodRecordFoods = new ArrayList<>();
}

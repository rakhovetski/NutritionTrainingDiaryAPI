package org.myproject.statisticservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "approach")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Approach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "number_of_repetitions")
    private Integer numberOfRepetitions;
    @Column(name = "extra_weight")
    private BigDecimal extraWeight;
    @JoinColumn(name = "exercise_record_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ExerciseRecord exerciseRecord;
}

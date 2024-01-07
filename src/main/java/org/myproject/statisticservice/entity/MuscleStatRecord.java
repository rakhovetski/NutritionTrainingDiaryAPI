package org.myproject.statisticservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "muscle_stat_record")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MuscleStatRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "muscle_girth")
    @Enumerated(EnumType.STRING)
    private Muscle muscleGirth;
    @Column(name = "girth_in_cm")
    private BigDecimal girthInCm;
    @JoinColumn(name = "body_stat_record_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BodyStatRecord bodyStatRecord;
}

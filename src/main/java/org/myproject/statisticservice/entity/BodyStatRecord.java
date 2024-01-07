package org.myproject.statisticservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "body_stat_record")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BodyStatRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "actual_weight")
    private BigDecimal actualWeight;
    @Column(name = "record_date")
    private LocalDateTime recordDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "bodyStatRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MuscleStatRecord> muscleStatRecords = new ArrayList<>();
}

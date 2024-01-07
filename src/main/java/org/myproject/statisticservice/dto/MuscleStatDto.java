package org.myproject.statisticservice.dto;

import lombok.Value;
import org.myproject.statisticservice.entity.Muscle;

import java.math.BigDecimal;

@Value
public class MuscleStatDto {
    Long id;
    Muscle muscleGirth;
    BigDecimal girthInCm;
}

package org.myproject.statisticservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectDetailBodyStatDto {
    private Long id;
    private BigDecimal actualWeight;
    private LocalDateTime localDateTime;
    private Long userId;
    private List<MuscleStatDto> muscleStatDtos;
}

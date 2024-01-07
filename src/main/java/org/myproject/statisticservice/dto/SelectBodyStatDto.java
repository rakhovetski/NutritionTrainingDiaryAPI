package org.myproject.statisticservice.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class SelectBodyStatDto {
    Long id;
    BigDecimal actualWeight;
    LocalDateTime localDateTime;
    Long userId;
}

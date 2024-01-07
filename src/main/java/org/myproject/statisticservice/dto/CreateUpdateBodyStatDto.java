package org.myproject.statisticservice.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class CreateUpdateBodyStatDto {
    BigDecimal actualWeight;
    LocalDateTime recordDate;
}

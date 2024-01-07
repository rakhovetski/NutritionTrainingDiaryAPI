package org.myproject.statisticservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetweenDateDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}

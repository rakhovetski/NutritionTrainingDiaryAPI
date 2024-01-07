package org.myproject.statisticservice.mappers;

import org.myproject.statisticservice.dto.SelectBodyStatDto;
import org.myproject.statisticservice.entity.BodyStatRecord;
import org.springframework.stereotype.Component;

@Component
public class BodyStatsMapper {

    public SelectBodyStatDto map(BodyStatRecord record) {
        return new SelectBodyStatDto(
                record.getId(),
                record.getActualWeight(),
                record.getRecordDate(),
                record.getUser().getId()
        );
    }
}

package org.myproject.statisticservice.mappers;

import lombok.RequiredArgsConstructor;
import org.myproject.statisticservice.dto.SelectDetailBodyStatDto;
import org.myproject.statisticservice.entity.BodyStatRecord;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DetailBodyStatsMapper {

    private final MuscleStatMapper muscleStatMapper;

    public SelectDetailBodyStatDto map(BodyStatRecord record) {
        return new SelectDetailBodyStatDto(
                record.getId(),
                record.getActualWeight(),
                record.getRecordDate(),
                record.getUser().getId(),
                record.getMuscleStatRecords().stream().map(
                    muscleStatMapper::map
                ).toList()
        );
    }
}

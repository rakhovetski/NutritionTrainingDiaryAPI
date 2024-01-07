package org.myproject.statisticservice.mappers;

import org.myproject.statisticservice.dto.MuscleStatDto;
import org.myproject.statisticservice.entity.MuscleStatRecord;
import org.springframework.stereotype.Component;

@Component
public class MuscleStatMapper {

    public MuscleStatDto map(MuscleStatRecord record) {
        return new MuscleStatDto(
                record.getId(),
                record.getMuscleGirth(),
                record.getGirthInCm()
        );
    }
}

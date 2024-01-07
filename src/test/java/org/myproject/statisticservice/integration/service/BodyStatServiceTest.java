package org.myproject.statisticservice.integration.service;

import org.junit.jupiter.api.Test;
import org.myproject.statisticservice.dto.CreateUpdateBodyStatDto;
import org.myproject.statisticservice.dto.SelectDetailBodyStatDto;
import org.myproject.statisticservice.exceptions.IncorrectBodyStatDataException;
import org.myproject.statisticservice.exceptions.NotFoundException;
import org.myproject.statisticservice.integration.IntegrationBaseTest;
import org.myproject.statisticservice.service.BodyStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Sql({
        "classpath:sql/user-data.sql"
})
public class BodyStatServiceTest extends IntegrationBaseTest {

    @Autowired
    private BodyStatsService bodyStatsService;

    private static final Long USER_ID = 1L;

    @Test
    public void testCorrectCreateBodyStatRecord() {
        CreateUpdateBodyStatDto createUpdateBodyStatDto =
                new CreateUpdateBodyStatDto(new BigDecimal("90.15"), LocalDateTime.now());

        var result = bodyStatsService.createBodyStatRecord(USER_ID, createUpdateBodyStatDto);

        assertEquals(2L, result.getId());
        assertEquals(new BigDecimal("90.15"), result.getActualWeight());
    }

    @Test
    public void testIncorrectCreateBodyStatRecord() {
        CreateUpdateBodyStatDto createUpdateBodyStatDto1 =
                new CreateUpdateBodyStatDto(null, LocalDateTime.now());

        assertThrows(IncorrectBodyStatDataException.class,
                () -> bodyStatsService.createBodyStatRecord(USER_ID, createUpdateBodyStatDto1));

        CreateUpdateBodyStatDto createUpdateBodyStatDto2 =
                new CreateUpdateBodyStatDto(new BigDecimal("100.00"), null);

        assertThrows(IncorrectBodyStatDataException.class,
                () -> bodyStatsService.createBodyStatRecord(USER_ID, createUpdateBodyStatDto2));
    }

    @Test
    public void testCorrectUpdateBodyStatRecord() {
        CreateUpdateBodyStatDto createUpdateBodyStatDto =
                new CreateUpdateBodyStatDto(new BigDecimal("90.15"), LocalDateTime.now());

        SelectDetailBodyStatDto createdRecord = bodyStatsService.createBodyStatRecord(USER_ID, createUpdateBodyStatDto);

        CreateUpdateBodyStatDto updateBodyStatDto =
                new CreateUpdateBodyStatDto(new BigDecimal("100.00"), LocalDateTime.now());

        var result = bodyStatsService.updateBodyStatRecord(USER_ID, createdRecord.getId(), updateBodyStatDto);

        assertEquals(createdRecord.getId(), result.getId());
        assertEquals(new BigDecimal("100.00"), result.getActualWeight());
    }

    @Test
    public void testIncorrectUpdateBodyStatRecord() {
        CreateUpdateBodyStatDto createUpdateBodyStatDto =
                new CreateUpdateBodyStatDto(new BigDecimal("90.15"), LocalDateTime.now());

        Long incorrect_user_id = 100L;
        Long incorrect_body_stat_id = 100L;

        assertThrows(NotFoundException.class, () -> bodyStatsService.updateBodyStatRecord(incorrect_user_id,
                incorrect_body_stat_id,
                createUpdateBodyStatDto));

        SelectDetailBodyStatDto createdRecord = bodyStatsService.createBodyStatRecord(USER_ID, createUpdateBodyStatDto);

        CreateUpdateBodyStatDto updateDto =
                new CreateUpdateBodyStatDto(null, null);

        assertThrows(IncorrectBodyStatDataException.class, () -> bodyStatsService.updateBodyStatRecord(USER_ID,
                createdRecord.getId(),
                updateDto));
    }

    @Test
    public void testCorrectDeleteBodyStat() {
        CreateUpdateBodyStatDto createUpdateBodyStatDto =
                new CreateUpdateBodyStatDto(new BigDecimal("90.15"), LocalDateTime.now());

        SelectDetailBodyStatDto createdRecord = bodyStatsService.createBodyStatRecord(USER_ID, createUpdateBodyStatDto);

        bodyStatsService.deleteBodyStatRecord(USER_ID, createdRecord.getId());

        assertThrows(NotFoundException.class, () -> bodyStatsService.findById(USER_ID,
                createdRecord.getId()));
    }

    @Test
    public void testIncorrectDeleteBodyStat() {
        Long incorrectUserId = 100L;
        Long incorrectBodyStatId = 100L;

        assertThrows(NotFoundException.class, () -> bodyStatsService.deleteBodyStatRecord(incorrectUserId,
                incorrectBodyStatId));
    }


}

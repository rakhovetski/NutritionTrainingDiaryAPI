package org.myproject.statisticservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.myproject.statisticservice.dto.CreateUpdateBodyStatDto;
import org.myproject.statisticservice.dto.SelectBodyStatDto;
import org.myproject.statisticservice.dto.SelectDetailBodyStatDto;
import org.myproject.statisticservice.entity.BodyStatRecord;
import org.myproject.statisticservice.entity.User;
import org.myproject.statisticservice.exceptions.IncorrectBodyStatDataException;
import org.myproject.statisticservice.exceptions.NotFoundException;
import org.myproject.statisticservice.mappers.BodyStatsMapper;
import org.myproject.statisticservice.mappers.DetailBodyStatsMapper;
import org.myproject.statisticservice.repository.BodyStatsRepository;
import org.myproject.statisticservice.repository.UserRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BodyStatsServiceTest {

    @Mock
    private BodyStatsRepository bodyStatsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DetailBodyStatsMapper detailBodyStatsMapper;
    @Mock
    private BodyStatsMapper bodyStatsMapper;
    @InjectMocks
    private BodyStatsService bodyStatsService;

    private static final Long USER_ID = 1L;
    private static final Long BODY_STAT_ID = 2L;

    @Test
    public void testCorrectUserIdFindBodyRecordById() {
        User user = new User();
        user.setId(USER_ID);
        BodyStatRecord bodyStatRecord = new BodyStatRecord(BODY_STAT_ID, new BigDecimal("67.1"), LocalDateTime.now(),
                user, Collections.emptyList());
        bodyStatRecord.setId(BODY_STAT_ID);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(bodyStatsRepository.findByIdAndUser(BODY_STAT_ID, user)).thenReturn(Optional.of(bodyStatRecord));
        when(detailBodyStatsMapper.map(bodyStatRecord)).thenReturn(new SelectDetailBodyStatDto(
                bodyStatRecord.getId(),
                bodyStatRecord.getActualWeight(),
                bodyStatRecord.getRecordDate(),
                bodyStatRecord.getUser().getId(),
                Collections.emptyList()
        ));

        SelectDetailBodyStatDto result = bodyStatsService.findById(USER_ID, BODY_STAT_ID);

        assertEquals(bodyStatRecord.getId(), result.getId());
        assertEquals(bodyStatRecord.getRecordDate(), result.getLocalDateTime());
        assertEquals(bodyStatRecord.getActualWeight(), result.getActualWeight());
    }

    @Test
    public void testIncorrectUserIdFindBodyRecordById() {
        boolean flag = false;

        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        try {
            bodyStatsService.findById(USER_ID, BODY_STAT_ID);
        } catch (NotFoundException exception) {
            flag = true;
        }

        assertTrue(flag);
    }

    @Test
    public void testCorrectCreateBodyStatRecord() {
        CreateUpdateBodyStatDto dto = new CreateUpdateBodyStatDto(new BigDecimal("69.1"), LocalDateTime.now());

        User user = new User();
        user.setId(USER_ID);

        BodyStatRecord bodyStatRecord = new BodyStatRecord(BODY_STAT_ID, dto.getActualWeight(), dto.getRecordDate(),
                user, Collections.emptyList());

        SelectDetailBodyStatDto selectDetailBodyStatDto = new SelectDetailBodyStatDto(
                bodyStatRecord.getId(), bodyStatRecord.getActualWeight(), bodyStatRecord.getRecordDate(),
                user.getId(), Collections.emptyList()
        );

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(bodyStatsRepository.save(any(BodyStatRecord.class))).thenReturn(bodyStatRecord);
        when(detailBodyStatsMapper.map(bodyStatRecord)).thenReturn(selectDetailBodyStatDto);

        SelectDetailBodyStatDto result = bodyStatsService.createBodyStatRecord(USER_ID, dto);

        assertNotNull(result);
        assertEquals(selectDetailBodyStatDto.getId(), result.getId());
        assertEquals(selectDetailBodyStatDto.getActualWeight(), result.getActualWeight());
        assertEquals(selectDetailBodyStatDto.getLocalDateTime(), result.getLocalDateTime());
    }

    @Test
    public void createIncorrectBodyStatRecordDto() {
        boolean flag = false;

        User user = new User();
        user.setId(USER_ID);

        CreateUpdateBodyStatDto createUpdateBodyStatDto = new CreateUpdateBodyStatDto(
                null,
                null
        );

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        try {
            bodyStatsService.createBodyStatRecord(USER_ID, createUpdateBodyStatDto);
        } catch (IncorrectBodyStatDataException ignored) {
            flag = true;
        }

        assertTrue(flag);
    }

    @Test
    public void updateCorrectBodyStatRecord() {

        User user = new User();
        user.setId(USER_ID);

        BodyStatRecord bodyStatRecord = new BodyStatRecord(
            BODY_STAT_ID, new BigDecimal("69.0"), LocalDateTime.now(), user, Collections.emptyList()
        );

        SelectDetailBodyStatDto selectDetailBodyStatDto = new SelectDetailBodyStatDto(
                bodyStatRecord.getId(), bodyStatRecord.getActualWeight(), bodyStatRecord.getRecordDate(),
                user.getId(), Collections.emptyList()
        );

        CreateUpdateBodyStatDto createUpdateBodyStatDto = new CreateUpdateBodyStatDto(
            bodyStatRecord.getActualWeight(), bodyStatRecord.getRecordDate()
        );

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(bodyStatsRepository.findByIdAndUser(BODY_STAT_ID, user)).thenReturn(Optional.of(bodyStatRecord));
        when(bodyStatsRepository.save(any(BodyStatRecord.class))).thenReturn(bodyStatRecord);
        when(detailBodyStatsMapper.map(bodyStatRecord)).thenReturn(selectDetailBodyStatDto);

        var result = bodyStatsService.updateBodyStatRecord(USER_ID, BODY_STAT_ID, createUpdateBodyStatDto);

        assertNotNull(result);
        assertEquals(bodyStatRecord.getActualWeight(), result.getActualWeight());
        assertEquals(bodyStatRecord.getRecordDate(), result.getLocalDateTime());
    }

    @Test
    public void updateIncorrectBodyStatRecord() {
        boolean flag = false;

        User user = new User();
        user.setId(USER_ID);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(bodyStatsRepository.findByIdAndUser(BODY_STAT_ID, user)).thenReturn(Optional.of(new BodyStatRecord()));

        CreateUpdateBodyStatDto createUpdateBodyStatDto = new CreateUpdateBodyStatDto(
                null,
                null
        );

        try {
            bodyStatsService.updateBodyStatRecord(USER_ID, BODY_STAT_ID, createUpdateBodyStatDto);
        } catch (IncorrectBodyStatDataException ignored) {
            flag = true;
        }

        assertTrue(flag);
    }

    @Test
    public void testCorrectFindAllBodyStatsByUserId() {
        User user = new User();
        user.setId(USER_ID);

        PageRequest pageRequest = PageRequest.of(0, 10);

        BodyStatRecord bodyStatRecord1 = new BodyStatRecord(
                BODY_STAT_ID, new BigDecimal("69.0"), LocalDateTime.now(), user, Collections.emptyList()
        );
        SelectBodyStatDto selectBodyStatDto = new SelectBodyStatDto(
                BODY_STAT_ID, bodyStatRecord1.getActualWeight(), bodyStatRecord1.getRecordDate(), user.getId()
        );

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(bodyStatsRepository.findAllByUser(user, pageRequest)).thenReturn(new PageImpl<>(
                List.of(
                        bodyStatRecord1, bodyStatRecord1
                ), pageRequest, 2
        ));
        when(bodyStatsMapper.map(any(BodyStatRecord.class))).thenReturn(selectBodyStatDto);

        var result = bodyStatsService.findAllBodyStatsByUserId(USER_ID, pageRequest);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());

        for(SelectBodyStatDto dto : result.getContent()) {
            assertEquals(selectBodyStatDto.getActualWeight(), dto.getActualWeight());
        }
    }
}
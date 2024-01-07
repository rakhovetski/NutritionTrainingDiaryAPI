package org.myproject.statisticservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.myproject.statisticservice.dto.*;
import org.myproject.statisticservice.entity.BodyStatRecord;
import org.myproject.statisticservice.entity.User;
import org.myproject.statisticservice.exceptions.ErrorCode;
import org.myproject.statisticservice.exceptions.IncorrectBodyStatDataException;
import org.myproject.statisticservice.exceptions.NotFoundException;
import org.myproject.statisticservice.mappers.BodyStatsMapper;
import org.myproject.statisticservice.mappers.DetailBodyStatsMapper;
import org.myproject.statisticservice.repository.BodyStatsRepository;
import org.myproject.statisticservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BodyStatsService {

    private final BodyStatsRepository bodyStatsRepository;
    private final UserRepository userRepository;
    private final BodyStatsMapper bodyStatsMapper;
    private final DetailBodyStatsMapper detailBodyStatsMapper;

    @Transactional
    public SelectDetailBodyStatDto createBodyStatRecord(Long userId, CreateUpdateBodyStatDto dto) {
        User user = getUserById(userId);

        validateBodyStat(dto);

        BodyStatRecord bodyStatRecord = BodyStatRecord.builder()
                .actualWeight(dto.getActualWeight())
                .recordDate(dto.getRecordDate())
                .user(user)
                .muscleStatRecords(Collections.emptyList())
                .build();
        log.info("Create BodyStatRecord by User with userId: " + userId);
        BodyStatRecord savedBodyStat = bodyStatsRepository.save(bodyStatRecord);
        return detailBodyStatsMapper.map(savedBodyStat);
    }

    @Transactional
    public SelectDetailBodyStatDto updateBodyStatRecord(Long userId, Long bodyStatId, CreateUpdateBodyStatDto dto) {
        User user = getUserById(userId);
        BodyStatRecord bodyStatRecord = getBodyStatRecordById(bodyStatId, user);

        validateBodyStat(dto);

        bodyStatRecord.setActualWeight(dto.getActualWeight());
        bodyStatRecord.setRecordDate(dto.getRecordDate());

        log.info("Update BodyStatRecord by User id: " + userId + " and by BodyStat Id: " + bodyStatId);
        BodyStatRecord savedBodyStat = bodyStatsRepository.save(bodyStatRecord);
        return detailBodyStatsMapper.map(savedBodyStat);
    }

    @Transactional
    public void deleteBodyStatRecord(Long userId, Long bodyStatId) {
        User user = getUserById(userId);
        BodyStatRecord bodyStatRecord = getBodyStatRecordById(bodyStatId, user);
        log.info("Delete BodyStatRecord by User id: " + userId + " and by BodyStat Id: " + bodyStatId);

        bodyStatsRepository.delete(bodyStatRecord);
    }

    public Page<SelectBodyStatDto> findAllBodyStatsByUserId(Long userId, PageRequest pageRequest) {
        User user = getUserById(userId);
        log.info("Try to find BodyStatRecords by User: " + user.getId());
        List<SelectBodyStatDto> bodyStatDtos = bodyStatsRepository.findAllByUser(user, pageRequest)
                .stream().map(bodyStatsMapper::map).toList();
        return new PageImpl<>(bodyStatDtos, pageRequest, bodyStatDtos.size());
    }

    public SelectDetailBodyStatDto findById(Long userId, Long id) {
        User user = getUserById(userId);
        return detailBodyStatsMapper.map(getBodyStatRecordById(id, user));
    }

    private User getUserById(Long userId) {
        log.info("Try to find User by userId: " + userId);
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(ErrorCode.INCORRECT_USER_ID.getErrorName()));
    }

    private BodyStatRecord getBodyStatRecordById(Long bodyStatId, User user) {
        log.info("Try to find BodyStatRecord by User and bodyStatId: " + bodyStatId);
        return bodyStatsRepository.findByIdAndUser(bodyStatId, user).orElseThrow(() ->
                new NotFoundException(ErrorCode.INCORRECT_BODY_STAT_ID.getErrorName()));
    }

    private void validateBodyStat(CreateUpdateBodyStatDto dto) {
        if (dto.getActualWeight() == null) {
            log.error("Incorrect actualWeight data for BodyStatRecord");
            throw new IncorrectBodyStatDataException(ErrorCode.INCORRECT_BODY_STAT_DATA.getErrorName());
        }
        if (dto.getRecordDate() == null) {
            log.error("Incorrect recordDate data for BodyStatRecord");
            throw new IncorrectBodyStatDataException(ErrorCode.INCORRECT_BODY_STAT_DATA.getErrorName());
        }
    }
}

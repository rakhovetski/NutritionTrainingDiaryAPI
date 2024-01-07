package org.myproject.statisticservice.integration.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.myproject.statisticservice.entity.BodyStatRecord;
import org.myproject.statisticservice.entity.User;
import org.myproject.statisticservice.integration.IntegrationBaseTest;
import org.myproject.statisticservice.repository.BodyStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Optional;

@Sql({
        "classpath:sql/user-data.sql",
        "classpath:sql/data.sql"
})
public class BodyStatRepositoryTest extends IntegrationBaseTest {

    @Autowired
    private BodyStatsRepository bodyStatsRepository;

    private static final Long USER_ID = 1L;
    private static final Long BODY_STAT_ID = 1L;

    private User user;

    @BeforeEach
    public void initializeUser(){
        user = new User();
        user.setId(USER_ID);
    }

    @Test
    public void testCorrectFindBodyStatsById() {
        Optional<BodyStatRecord> result = bodyStatsRepository.findByIdAndUser(BODY_STAT_ID, user);

        Assertions.assertTrue(result.isPresent());

        result.ifPresent(
                obj -> {
                    Assertions.assertEquals(new BigDecimal("67.10"), obj.getActualWeight());
                    Assertions.assertEquals(BODY_STAT_ID, obj.getId());
                }
        );
    }

    @Test
    public void testIncorrectFindBodyStatsById() {
        Optional<BodyStatRecord> result = bodyStatsRepository.findByIdAndUser(BODY_STAT_ID + 100L, user);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testCorrectFindAllBodyStatsByUser() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        var result = bodyStatsRepository.findAllByUser(user, pageRequest);

        Assertions.assertEquals(3, result.getTotalElements());

        var firstBodyStat = result.getContent().getFirst();

        Assertions.assertEquals(new BigDecimal("67.10"), firstBodyStat.getActualWeight());

        var lastBodyStat = result.getContent().getLast();

        Assertions.assertEquals(new BigDecimal("83.10"), lastBodyStat.getActualWeight());
    }
}

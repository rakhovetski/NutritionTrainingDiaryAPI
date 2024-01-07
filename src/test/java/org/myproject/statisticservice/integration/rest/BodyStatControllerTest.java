package org.myproject.statisticservice.integration.rest;

import org.junit.jupiter.api.Test;
import org.myproject.statisticservice.integration.IntegrationBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Sql({
        "classpath:sql/user-data.sql",
        "classpath:sql/data.sql"
})
public class BodyStatControllerTest extends IntegrationBaseTest{

    @Autowired
    private MockMvc mockMvc;

    private static final Long USER_ID = 1L;
    private static final Long BODY_STAT_ID = 1L;

    private static final String URL_WITH_USER_ID = "/api/v1/{userId}/body-stats";
    private static final String URL_WITH_USER_ID_AND_BODY_STAT_ID = URL_WITH_USER_ID + "/{id}";


    @Test
    public void testFindAllBodyStatsByUserId() throws Exception {
        mockMvc.perform(get(URL_WITH_USER_ID, USER_ID)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    public void testIncorrectFindAllBodyStatsByUserId() throws Exception {
        Long incorrectUserId = 100L;
        mockMvc.perform(get(URL_WITH_USER_ID, incorrectUserId)
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testFindBodyStatByIdAndByUserId() throws Exception {
        mockMvc.perform(get(URL_WITH_USER_ID_AND_BODY_STAT_ID, USER_ID, BODY_STAT_ID)
        ).andExpect(status().isOk());
    }

    @Test
    public void testIncorrectFindBodyStatByIdAndByUserId() throws Exception {
        Long incorrectBodyStatId = 100L;
        mockMvc.perform(get(URL_WITH_USER_ID_AND_BODY_STAT_ID, USER_ID, incorrectBodyStatId))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testDeleteBodyStatByUserIdAndBodyStatId() throws Exception {
        mockMvc.perform(delete(URL_WITH_USER_ID_AND_BODY_STAT_ID, USER_ID, BODY_STAT_ID)
        ).andExpect(status().isOk());
    }

    @Test
    public void testIncorrectDeleteBodyStatByUserIdAndBodyStatId() throws Exception {
        Long incorrectBodyStatId = 100L;
        mockMvc.perform(delete(URL_WITH_USER_ID_AND_BODY_STAT_ID, USER_ID, incorrectBodyStatId))
                .andExpect(status().is4xxClientError());
    }
}

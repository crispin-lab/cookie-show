package com.crispinlab.cookieshow.controller

import com.crispinlab.cookieshow.application.service.PerformanceService
import com.crispinlab.cookieshow.application.service.dto.CreatePerformanceRequest
import com.crispinlab.cookieshow.controller.dto.CreatePerformanceResponse
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Instant
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(PerformanceController::class)
class PerformanceControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockitoBean
    private lateinit var performanceService: PerformanceService

    @Nested
    @DisplayName("PerformanceController")
    inner class PerformanceControllerTest {
        @Nested
        @DisplayName("Success")
        inner class PerformanceControllerSuccessTest {
            @Test
            @DisplayName("공연 생성 요청 테스트")
            fun createPerformanceTest() {
                // given
                val request =
                    CreatePerformanceRequest(
                        title = "테스트 공연",
                        description = "공연 등록 테스트 중입니다.",
                        venue = 1L,
                        startTime = Instant.now().plusSeconds(60),
                        endTime = Instant.now().plusSeconds(720)
                    )

                // mock
                val registerRequest =
                    CreatePerformanceRequest(
                        title = request.title,
                        description = request.description,
                        venue = request.venue,
                        startTime = request.startTime,
                        endTime = request.endTime,
                        reservationStartTime = request.reservationStartTime,
                        reservationEndTime = request.reservationEndTime
                    )
                val registerResponse =
                    CreatePerformanceResponse(
                        id = 1L,
                        title = request.title,
                        description = request.description,
                        venue = request.venue,
                        startTime = request.startTime,
                        endTime = request.endTime,
                        reservationStartTime = Instant.now(),
                        reservationEndTime = Instant.now()
                    )
                `when`(performanceService.register(registerRequest)).thenReturn(registerResponse)

                // when
                val result: ResultActions =
                    mockMvc
                        .perform(
                            MockMvcRequestBuilders
                                .post("/api/performance")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                        ).andDo(MockMvcResultHandlers.print())

                // then
                result
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("SUCCESS"))
            }
        }
    }
}

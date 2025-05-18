package com.crispinlab.cookieshow.application.service

import com.crispinlab.cookieshow.application.usecase.PerformanceRegisterUseCase
import com.crispinlab.cookieshow.repository.PerformanceRepository
import com.crispinlab.cookieshow.repository.VenueRepository
import com.crispinlab.cookieshow.repository.entity.PerformanceEntity
import org.assertj.core.api.Assertions
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PerformanceServiceTest {
    @Mock
    private lateinit var performanceRepository: PerformanceRepository

    @Mock
    private lateinit var venueRepository: VenueRepository

    @InjectMocks
    private lateinit var performanceService: PerformanceService

    @Nested
    @DisplayName("PerformanceRegister")
    inner class PerformanceRegisterTest {
        @Nested
        @DisplayName("Success")
        inner class PerformanceRegisterSuccessTest {
            @Test
            @DisplayName("공연 등록 성공 테스트")
            fun registerPerformanceTest() {
                // given
                val request =
                    PerformanceRegisterUseCase.RegisterRequest(
                        title = "테스트 공연",
                        description = "테스트 용도 공연 입니다.",
                        venue = 1
                    )

                // mock
                val mockedPerformanceEntity: PerformanceEntity = mock(PerformanceEntity::class.java)
                `when`(mockedPerformanceEntity.id).thenReturn(1L)
                `when`(performanceRepository.save(any())).thenReturn(mockedPerformanceEntity)
                `when`(venueRepository.existsById(request.venue)).thenReturn(true)

                // when
                val actual: PerformanceRegisterUseCase.RegisterResponse =
                    performanceService.register(request)

                // then
                SoftAssertions.assertSoftly { softAssertions ->
                    softAssertions.assertThat(actual.id).isEqualTo(1L)
                    softAssertions.assertThat(actual.title).isEqualTo(request.title)
                    softAssertions.assertThat(actual.description).isEqualTo(request.description)
                    softAssertions.assertThat(actual.venue).isEqualTo(request.venue)
                }

                verify(venueRepository).existsById(1)
                verify(performanceRepository).save(any())
            }
        }

        @Nested
        @DisplayName("Fail")
        inner class PerformanceRegisterFailTest {
            @Test
            @DisplayName("공연 등록 실패 테스트")
            fun registerPerformanceFailTest() {
                // given
                val request =
                    PerformanceRegisterUseCase.RegisterRequest(
                        title = "테스트 공연",
                        description = "테스트 용도 공연 입니다.",
                        venue = 1
                    )

                // mock
                `when`(venueRepository.existsById(request.venue)).thenReturn(false)

                // when & then
                Assertions
                    .assertThatThrownBy {
                        performanceService.register(request)
                    }.isInstanceOf(IllegalArgumentException::class.java)

                verify(venueRepository).existsById(1)
            }
        }
    }
}

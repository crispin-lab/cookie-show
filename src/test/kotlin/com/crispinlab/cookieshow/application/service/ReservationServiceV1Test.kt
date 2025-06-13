package com.crispinlab.cookieshow.application.service

import com.crispinlab.cookieshow.application.service.dto.CreateReservationRequest
import com.crispinlab.cookieshow.repository.PerformanceRepository
import com.crispinlab.cookieshow.repository.entity.PerformanceEntity
import java.time.Instant
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ReservationServiceV1Test {
    @Autowired
    lateinit var reservationServiceV1: ReservationServiceV1

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @AfterEach
    fun tearDown() {
        performanceRepository.deleteAll()
    }

    @BeforeEach
    fun setUp() {
        performanceRepository.save<PerformanceEntity>(
            PerformanceEntity(
                title = "테스트 공연",
                description = "테스트 중",
                venue = 1L,
                startTime = Instant.now().plusSeconds(60),
                endTime = Instant.now().plusSeconds(120),
                reservationStartTime = Instant.now().minusSeconds(10),
                reservationEndTime = Instant.now().plusSeconds(20)
            )
        )
    }

    @Test
    @DisplayName("동시성 처리 테스트")
    fun reservationServiceV1Test() {
        // given
        val setId = 1L
        val performanceId = 1L
        val user =
            CreateReservationRequest.ReservationUser(
                name = "테스터1",
                phone = "123-1234-1234",
                email = "tester1@example.com"
            )
        val request =
            CreateReservationRequest(
                performance = performanceId,
                seat = setId,
                user = user,
                reservationRequestTime = Instant.now()
            )

        val successCounter = AtomicInteger(0)
        val latch = CountDownLatch(10)
        val executor: ExecutorService = Executors.newFixedThreadPool(10)

        // when
        repeat(10) {
            executor.submit {
                try {
                    reservationServiceV1.reserve(request)
                    successCounter.incrementAndGet()
                } catch (_: Exception) {
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        // then
        Assertions.assertThat(successCounter.get()).isEqualTo(1)
    }
}

package com.crispinlab.cookieshow.repository

import com.crispinlab.cookieshow.repository.entity.SeatEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class SeatRepositoryTest {
    @Autowired
    private lateinit var seatRepository: SeatRepository

    @Autowired
    private lateinit var testEntityManager: TestEntityManager

    @Test
    @DisplayName("Query Method 동작 테스트 - findAllByVenue(venueId: Long)")
    fun fundAllByVenueQueryMethodTest() {
        // given
        val seats: List<SeatEntity> =
            List(size = 10) { i ->
                SeatEntity(
                    venue = Long.MAX_VALUE,
                    row = "A열",
                    number = i
                )
            }
        seats.forEach { testEntityManager.persist(it) }
        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val foundSeats: List<SeatEntity> = seatRepository.findAllByVenue(Long.MAX_VALUE)

        // then
        Assertions.assertThat(foundSeats.count()).isEqualTo(10)
    }
}

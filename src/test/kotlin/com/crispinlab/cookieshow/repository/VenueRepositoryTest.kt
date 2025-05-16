package com.crispinlab.cookieshow.repository

import com.crispinlab.cookieshow.repository.entity.VenueEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import kotlin.test.Test
import org.assertj.core.api.Assertions
import org.assertj.core.api.SoftAssertions
import org.hibernate.Session
import org.hibernate.stat.Statistics
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class VenueRepositoryTest {
    @Autowired
    private lateinit var venueRepository: VenueRepository

    @Autowired
    private lateinit var testEntityManager: TestEntityManager

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @BeforeEach
    fun setUp() {
        val stats: Statistics =
            entityManager.unwrap(Session::class.java).sessionFactory.statistics
        stats.isStatisticsEnabled = true
        stats.clear()
    }

    @Test
    @DisplayName("findById 호출 시 rows 컬렉션이 함께 로드되는지 검증")
    fun findByIdShouldLoadRowsCollection() {
        // given
        val venueWithRows =
            VenueEntity(
                name = "Test Venue",
                address = "123 Test Street",
                capacity = 1000,
                rows = listOf(1L, 2L, 3L)
            )

        val savedVenue: VenueEntity = testEntityManager.persistAndFlush(venueWithRows)
        testEntityManager.clear()

        // when
        val foundVenue: VenueEntity = venueRepository.findById(savedVenue.id).orElseThrow()

        // then
        SoftAssertions.assertSoftly {
            it.assertThat(foundVenue.rows.size).isEqualTo(3)
            it.assertThatCollection(foundVenue.rows).containsAll(listOf(1L, 2L, 3L))
        }
    }

    @Test
    @DisplayName("rows 컬렉션이 몇 개의 쿼리로 로드되는지 검증")
    fun verifyIfRowsAreLoadedWithSingleQuery() {
        // given
        val venueWithRows =
            VenueEntity(
                name = "Performance Hall",
                address = "456 Concert Ave",
                capacity = 2000,
                rows = listOf(10L, 20L, 30L, 40L)
            )

        val savedVenue: VenueEntity = testEntityManager.persistAndFlush(venueWithRows)

        testEntityManager.clear()

        val stats: Statistics =
            entityManager.unwrap(Session::class.java).sessionFactory.statistics
        stats.clear()

        // when
        val foundVenue: VenueEntity = venueRepository.findById(savedVenue.id).orElseThrow()

        // then
        val rowsList: List<Long> = foundVenue.rows.toList()
        val queryCount: Long = stats.prepareStatementCount

        println("SQL 쿼리 실행 횟수: $queryCount")

        SoftAssertions.assertSoftly {
            it.assertThat(rowsList.size).isEqualTo(4)
            it.assertThatCollection(rowsList).containsAll(listOf(10L, 20L, 30L, 40L))
        }
    }

    @Test
    @DisplayName("로딩 동작 시점 확인 - EAGER vs LAZY")
    fun testEagerVsLazyLoadingBehavior() {
        // given
        val venueWithRows =
            VenueEntity(
                name = "Stadium",
                address = "789 Sports Blvd",
                capacity = 5000,
                rows = listOf(100L, 200L, 300L)
            )

        val savedVenue: VenueEntity = testEntityManager.persistAndFlush(venueWithRows)
        testEntityManager.clear()

        val stats: Statistics =
            entityManager.unwrap(Session::class.java).sessionFactory.statistics
        stats.clear()

        // when
        val foundVenue: VenueEntity = venueRepository.findById(savedVenue.id).orElseThrow()

        // then
        val queryCountAfterFindById: Long = stats.prepareStatementCount
        println("findById 직후 SQL 쿼리 실행 횟수: $queryCountAfterFindById")

        val rowsSize: Int = foundVenue.rows.size
        val queryCountAfterAccess: Long = stats.prepareStatementCount

        println("컬렉션 접근 후 SQL 쿼리 실행 횟수: $queryCountAfterAccess")

        Assertions.assertThat(rowsSize).isEqualTo(3)

        if (queryCountAfterFindById == queryCountAfterAccess) {
            println("EAGER 로딩: findById로 모든 데이터가 한번에 조회됨")
        } else {
            println("LAZY 로딩: 컬렉션 접근 시 추가 쿼리 발생")
        }
    }
}

package com.example.androidui.data.repository

import com.example.androidui.data.dto.AppDto
import com.example.androidui.data.local.AppDetailsDao
import com.example.androidui.data.local.AppDetailsEntity
import com.example.androidui.data.mapper.AppDetailsEntityMapper
import com.example.androidui.data.mapper.AppMapper
import com.example.androidui.data.network.AppApiService
import com.example.androidui.domain.model.App
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppRepositoryImplTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val mapper = mockk<AppMapper>()
    private val entityMapper = mockk<AppDetailsEntityMapper>()
    private val api = mockk<AppApiService>()
    private val dao = mockk<AppDetailsDao>()
    private lateinit var repository: AppRepositoryImpl

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = AppRepositoryImpl(mapper, entityMapper, api, dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test 1 - getApps fetches from API and returns mapped list`() = runTest {
        val dtos = listOf(AppDto("1", "A1", "D1", "C1", "U1"))
        val domains = listOf(App("1", "A1", "D1", "C1", "U1", false))
        val entity = AppDetailsEntity("1", "A1", "D1", "C1", "U1", false)

        coEvery { api.getCatalog() } returns dtos
        coEvery { dao.getAllAppDetails() } returns emptyList()
        coEvery { dao.insertAllAppDetails(any()) } just Runs
        every { entityMapper.toEntity(any()) } returns entity
        every { mapper.toDomain(any<AppDto>()) } returnsMany domains

        val result = repository.getApps()
        assertEquals(domains, result)
    }

    @Test
    fun `test 2 - getApps preserves wishlist from cache when API fails`() = runTest {
        coEvery { api.getCatalog() } throws Exception("Network error")
        val cachedEntity = AppDetailsEntity("1", "App", "Desc", "Cat", "url", true)
        coEvery { dao.getAllAppDetails() } returns listOf(cachedEntity)
        every { entityMapper.toDomain(cachedEntity) } returns App("1", "App", "Desc", "Cat", "url", true)
        val result = repository.getApps()
        assertTrue(result.first().isInWishlist)
        coVerify { api.getCatalog() }
        coVerify { dao.getAllAppDetails() }
    }

    @Test
    fun `test 3 - getApps returns cache when API fails`() = runTest {
        val cachedEntity = AppDetailsEntity("1", "Cached", "D", "C", "U", false)
        val cached = App("1", "Cached", "D", "C", "U", false)

        coEvery { api.getCatalog() } throws Exception("Network error")
        coEvery { dao.getAllAppDetails() } returns listOf(cachedEntity)
        every { entityMapper.toDomain(cachedEntity) } returns cached

        val result = repository.getApps()
        assertEquals(listOf(cached), result)
    }

    @Test
    fun `test 4 - getApps throws when API fails and cache empty`() = runTest {
        coEvery { api.getCatalog() } throws Exception("Network error")
        coEvery { dao.getAllAppDetails() } returns emptyList()

        val result = runCatching { repository.getApps() }
        assertTrue(result.isFailure)
    }

    @Test
    fun `test 5 - toggleWishlist updates status to true`() = runTest {
        val id = "123"
        val entity = AppDetailsEntity(id, "A", "D", "C", "U", false)

        coEvery { dao.getAppDetails(id) } returns flowOf(entity)
        coEvery { dao.updateWishlistStatus(id, true) } just Runs

        repository.toggleWishlist(id)

        coVerify { dao.updateWishlistStatus(id, true) }
    }
}

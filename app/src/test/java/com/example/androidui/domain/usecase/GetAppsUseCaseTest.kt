package com.example.androidui.domain.usecase

import com.example.androidui.domain.model.App
import com.example.androidui.domain.repository.AppRepository
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetAppsUseCaseTest {

    private val repository = mockk<AppRepository>()
    private lateinit var useCase: GetAppsUseCase

    @Before
    fun setup() {
        useCase = GetAppsUseCase(repository)
    }

    @Test
    fun `test 1 - invoke delegates to repository`() = runTest {
        val expected = listOf(App("1", "A", "D", "C", "U", false))
        coEvery { repository.getApps() } returns expected

        val result = useCase()

        assertEquals(expected, result)
        coVerify { repository.getApps() }
    }

    @Test
    fun `test 2 - invoke returns empty list`() = runTest {
        coEvery { repository.getApps() } returns emptyList()

        val result = useCase()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `test 3 - invoke propagates exceptions`() = runTest {
        coEvery { repository.getApps() } throws Exception("Error")

        val result = runCatching { useCase() }

        assertTrue(result.isFailure)
    }

    @Test
    fun `test 4 - invoke is idempotent`() = runTest {
        val apps = listOf(App("1", "A", "D", "C", "U", false))
        coEvery { repository.getApps() } returns apps

        assertEquals(useCase(), useCase())
    }

    @Test
    fun `test 5 - invoke calls repository once per execution`() = runTest {
        coEvery { repository.getApps() } returns emptyList()

        useCase()
        useCase()

        coVerify(exactly = 2) { repository.getApps() }
    }
}
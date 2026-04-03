package com.example.androidui.data.mapper

import com.example.androidui.data.dto.AppDto
import org.junit.Assert.*
import org.junit.Test

class AppMapperTest {

    private val mapper = AppMapper()

    @Test
    fun `test 1 - toDomain maps all fields`() {
        val dto = AppDto("1", "App", "Desc", "Cat", "url")
        val domain = mapper.toDomain(dto)
        assertEquals(dto.id, domain.id)
        assertEquals(dto.name, domain.name)
        assertEquals(dto.description, domain.description)
        assertEquals(dto.category, domain.category)
        assertEquals(dto.iconUrl, domain.iconUrl)
    }

    @Test
    fun `test 2 - isInWishlist false by default`() {
        val dto = AppDto("1", "A", "D", "C", "U")
        val domain = mapper.toDomain(dto)
        assertFalse(domain.isInWishlist)
    }

    @Test
    fun `test 3 - empty strings handled`() {
        val dto = AppDto("", "", "", "", "")
        val domain = mapper.toDomain(dto)
        assertEquals("", domain.id)
        assertEquals("", domain.name)
    }

    @Test
    fun `test 4 - special chars preserved`() {
        val dto = AppDto("a-b", "App™", "Desc\n", "Cat&", "https://x?a=1")
        val domain = mapper.toDomain(dto)
        assertEquals(dto.name, domain.name)
        assertEquals(dto.iconUrl, domain.iconUrl)
    }

    @Test
    fun `test 5 - equal instances`() {
        val dto = AppDto("1", "A", "D", "C", "U")
        assertEquals(mapper.toDomain(dto), mapper.toDomain(dto))
    }
}
package com.example.androidui.data.mapper

import com.example.androidui.data.dto.AppDto
import com.example.androidui.data.local.AppDetailsEntity
import org.junit.Assert.*
import org.junit.Test

class AppDetailsEntityMapperTest {

    private val mapper = AppDetailsEntityMapper()

    @Test
    fun `test 1 - toEntity maps all fields`() {
        val dto = AppDto("1", "App", "Desc", "Cat", "url")
        val entity = mapper.toEntity(dto)
        assertEquals(dto.id, entity.id)
        assertEquals(dto.name, entity.name)
        assertEquals(dto.description, entity.description)
        assertEquals(dto.category, entity.category)
        assertEquals(dto.iconUrl, entity.iconUrl)
    }

    @Test
    fun `test 2 - toDomain maps all fields with wishlist true`() {
        val entity = AppDetailsEntity("1", "App", "Desc", "Cat", "url", true)
        val domain = mapper.toDomain(entity)
        assertEquals(entity.id, domain.id)
        assertEquals(entity.name, domain.name)
        assertEquals(entity.description, domain.description)
        assertEquals(entity.category, domain.category)
        assertEquals(entity.iconUrl, domain.iconUrl)
        assertTrue(domain.isInWishlist)
    }

    @Test
    fun `test 3 - toDomain sets wishlist false by default`() {
        val entity = AppDetailsEntity("1", "App", "Desc", "Cat", "url", false)
        val domain = mapper.toDomain(entity)
        assertFalse(domain.isInWishlist)
    }

    @Test
    fun `test 4 - toEntity preserves empty strings`() {
        val dto = AppDto("", "", "", "", "")
        val entity = mapper.toEntity(dto)
        assertEquals("", entity.id)
        assertEquals("", entity.name)
    }

    @Test
    fun `test 5 - round trip conversion`() {
        val dto = AppDto("1", "App", "Desc", "Cat", "url")
        val entity = mapper.toEntity(dto)
        val domain = mapper.toDomain(entity)
        assertEquals(dto.id, domain.id)
        assertEquals(dto.name, domain.name)
        assertFalse(domain.isInWishlist)
    }
}
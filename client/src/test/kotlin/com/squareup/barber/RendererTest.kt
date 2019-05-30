package com.squareup.barber

import com.squareup.barber.RealRenderer.Companion.renderMustache
import com.squareup.barber.models.CopyModel
import com.squareup.barber.models.DocumentSpec
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class RendererTest {
  @Test
  fun basic() {
    val person = Person(
      name = "World"
    )
    val template = "Hello {{name}}!"
    assertEquals("Hello World!", renderMustache(template, person))
  }

  data class Person (
    val name: String
  ): CopyModel
}
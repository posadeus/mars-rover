package com.posadeus.rover.domain

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class MarsTest {

  @Test
  internal fun `define Mars surface`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    assertTrue { mars.coordinate(1, 1) == Coordinate(-1, -1) }
  }
}
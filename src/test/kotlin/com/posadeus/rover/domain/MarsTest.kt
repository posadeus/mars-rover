package com.posadeus.rover.domain

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MarsTest {

  @Test
  internal fun `define Mars surface`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    assertTrue { mars.isValidCoordinate(Coordinate(1, 1)) }
    assertFalse { mars.isValidCoordinate(Coordinate(-3, 1)) }
  }
}
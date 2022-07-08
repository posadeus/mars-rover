package com.posadeus.rover.domain

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MarsTest {

  @Test
  internal fun `define Mars surface`() {

    val mars = Mars(arrayOf(arrayOf(Empty, Empty, Empty, Rock, Empty),
                            arrayOf(Empty, Rock, Empty, Empty, Empty),
                            arrayOf(Empty, Empty, Empty, Empty, Empty),
                            arrayOf(Empty, Empty, Empty, Empty, Rock),
                            arrayOf(Empty, Rock, Rock, Empty, Empty)))

    assertTrue { mars.isValidCoordinate(Coordinate(1, 1)) }
    assertFalse { mars.isValidCoordinate(Coordinate(-3, 1)) }
  }

  @Test
  internal fun `Mars surface with obstacles`() {

    val mars = Mars(arrayOf(arrayOf(Empty, Empty, Empty, Rock, Empty),
                            arrayOf(Empty, Rock, Empty, Empty, Empty),
                            arrayOf(Empty, Empty, Empty, Empty, Empty),
                            arrayOf(Empty, Empty, Empty, Empty, Rock),
                            arrayOf(Empty, Rock, Rock, Empty, Empty)))

    assertTrue { mars.hasObstacle(Coordinate(0, 3)) }
    assertFalse { mars.hasObstacle(Coordinate(1, 2)) }
    assertFalse { mars.hasObstacle(Coordinate(5, 2)) }
  }
}
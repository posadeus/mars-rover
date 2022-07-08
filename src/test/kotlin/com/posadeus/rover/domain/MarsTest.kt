package com.posadeus.rover.domain

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MarsTest {

  @Test
  internal fun `define Mars surface`() {

    val mars = Mars(arrayOf(arrayOf(null, null, null, Obstacle, null),
                            arrayOf(null, Obstacle, null, null, null),
                            arrayOf(null, null, null, null, null),
                            arrayOf(null, null, null, null, Obstacle),
                            arrayOf(null, Obstacle, Obstacle, null, null)))

    assertTrue { mars.isValidCoordinate(Coordinate(1, 1)) }
    assertFalse { mars.isValidCoordinate(Coordinate(-3, 1)) }
  }

  @Test
  internal fun `Mars surface with obstacles`() {

    val mars = Mars(arrayOf(arrayOf(null, null, null, Obstacle, null),
                            arrayOf(null, Obstacle, null, null, null),
                            arrayOf(null, null, null, null, null),
                            arrayOf(null, null, null, null, Obstacle),
                            arrayOf(null, Obstacle, Obstacle, null, null)))

    assertTrue { mars.hasObstacle(Coordinate(0, 3)) }
    assertFalse { mars.hasObstacle(Coordinate(1, 2)) }
  }
}
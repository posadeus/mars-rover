package com.posadeus.rover.domain

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MarsTest {

  @Test
  internal fun `define Mars surface`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(arrayOf(null, null, null, Obstacle, null),
                            arrayOf(null, Obstacle, null, null, null),
                            arrayOf(null, null, null, null, null),
                            arrayOf(null, null, null, null, Obstacle),
                            arrayOf(null, Obstacle, Obstacle, null, null)))

    assertTrue { mars.isValidCoordinateNew(Coordinate(1, 1)) }
    assertFalse { mars.isValidCoordinateNew(Coordinate(-3, 1)) }
  }

  @Test
  internal fun `Mars surface with obstacles`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(arrayOf(null, null, null, Obstacle, null),
                            arrayOf(null, Obstacle, null, null, null),
                            arrayOf(null, null, null, null, null),
                            arrayOf(null, null, null, null, Obstacle),
                            arrayOf(null, Obstacle, Obstacle, null, null)))

    assertTrue { mars.hasObstacleNew(Coordinate(0, 3)) }
    assertFalse { mars.hasObstacleNew(Coordinate(1, 2)) }
  }
}
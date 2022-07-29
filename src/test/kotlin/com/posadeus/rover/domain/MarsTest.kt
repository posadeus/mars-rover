package com.posadeus.rover.domain

import arrow.core.Either
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
    val error = Either.Left(WrongCoordinate("Coordinate not allowed: ${Coordinate(-3, 1)}"))

    assertTrue { mars.isValidCoordinateNew(Coordinate(1, 1)).isRight() }
    assertFalse { mars.isValidCoordinateNew(Coordinate(-3, 1)).isRight() }
    assertTrue { mars.isValidCoordinateNew(Coordinate(-3, 1)) == error }
  }

  @Test
  internal fun `Mars surface with obstacles`() {

    val mars = Mars(arrayOf(arrayOf(Empty, Empty, Empty, Rock, Empty),
                            arrayOf(Empty, Rock, Empty, Empty, Empty),
                            arrayOf(Empty, Empty, Empty, Empty, Empty),
                            arrayOf(Empty, Empty, Empty, Empty, Rock),
                            arrayOf(Empty, Rock, Rock, Empty, Empty)))
    val error = Either.Left(WrongCoordinate("Coordinate not allowed: ${Coordinate(5, 2)}"))

    assertTrue { mars.hasObstacleNew(Coordinate(0, 3)) == Either.Right(true) }
    assertTrue { mars.hasObstacleNew(Coordinate(1, 2)) == Either.Right(false) }
    assertTrue { mars.hasObstacleNew(Coordinate(5, 2)) == error }
  }
}
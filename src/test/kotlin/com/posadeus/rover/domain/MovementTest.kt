package com.posadeus.rover.domain

import arrow.core.getOrElse
import com.posadeus.rover.domain.Orientation.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MovementTest {

  private val movement = Movement()

  @Test
  internal fun `move forward`() {

    assertTrue { movement.move(mars, Coordinate(2, 2), 'f', N).getOrElse { null } == Coordinate(2, 3) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'f', E).getOrElse { null } == Coordinate(3, 2) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'f', S).getOrElse { null } == Coordinate(2, 1) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'f', W).getOrElse { null } == Coordinate(1, 2) }
  }

  @Test
  internal fun `move backward`() {

    assertTrue { movement.move(mars, Coordinate(2, 2), 'b', N).getOrElse { null } == Coordinate(2, 1) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'b', E).getOrElse { null } == Coordinate(1, 2) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'b', S).getOrElse { null } == Coordinate(2, 3) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'b', W).getOrElse { null } == Coordinate(3, 2) }
  }

  @Test
  internal fun `move forward over the edges`() {

    assertTrue { movement.move(mars, Coordinate(0, 4), 'f', N).getOrElse { null } == Coordinate(0, 0) }
    assertTrue { movement.move(mars, Coordinate(4, 0), 'f', E).getOrElse { null } == Coordinate(0, 0) }
    assertTrue { movement.move(mars, Coordinate(0, 0), 'f', S).getOrElse { null } == Coordinate(0, 4) }
    assertTrue { movement.move(mars, Coordinate(0, 0), 'f', W).getOrElse { null } == Coordinate(4, 0) }
  }

  @Test
  internal fun `move backward over the edges`() {

    assertTrue { movement.move(mars, Coordinate(0, 0), 'b', N).getOrElse { null } == Coordinate(0, 4) }
    assertTrue { movement.move(mars, Coordinate(0, 0), 'b', E).getOrElse { null } == Coordinate(4, 0) }
    assertTrue { movement.move(mars, Coordinate(0, 4), 'b', S).getOrElse { null } == Coordinate(0, 0) }
    assertTrue { movement.move(mars, Coordinate(4, 0), 'b', W).getOrElse { null } == Coordinate(0, 0) }
  }

  @Test
  internal fun `move command not found`() {

    val move = movement.move(mars, Coordinate(0, 0), 'k', N)

    assertTrue { move.isLeft() }
    assertTrue { move.fold({ it }, { it }) == CommandNotFound }
  }

  companion object {

    private val mars = Mars(arrayOf(arrayOf(Empty, Empty, Empty, Rock, Empty),
                                    arrayOf(Empty, Rock, Empty, Empty, Empty),
                                    arrayOf(Empty, Empty, Empty, Empty, Empty),
                                    arrayOf(Empty, Empty, Empty, Empty, Rock),
                                    arrayOf(Empty, Rock, Rock, Empty, Empty)))
  }
}
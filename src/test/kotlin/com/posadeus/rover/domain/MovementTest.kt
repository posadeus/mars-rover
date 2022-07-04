package com.posadeus.rover.domain

import com.posadeus.rover.domain.Orientation.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MovementTest {

  private val movement = Movement()

  @Test
  internal fun `move forward`() {

    assertTrue { movement.move(Coordinate(0, 0), 'f', N) == Coordinate(0, 1) }
    assertTrue { movement.move(Coordinate(0, 0), 'f', E) == Coordinate(1, 0) }
    assertTrue { movement.move(Coordinate(0, 0), 'f', S) == Coordinate(0, -1) }
    assertTrue { movement.move(Coordinate(0, 0), 'f', W) == Coordinate(-1, 0) }
  }

  @Test
  internal fun `move backward`() {

    assertTrue { movement.move(Coordinate(0, 0), 'b', N) == Coordinate(0, -1) }
    assertTrue { movement.move(Coordinate(0, 0), 'b', E) == Coordinate(-1, 0) }
    assertTrue { movement.move(Coordinate(0, 0), 'b', S) == Coordinate(0, 1) }
    assertTrue { movement.move(Coordinate(0, 0), 'b', W) == Coordinate(1, 0) }
  }
}
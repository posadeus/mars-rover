package com.posadeus.rover.domain

import com.posadeus.rover.domain.Orientation.*
import com.posadeus.rover.domain.exception.CommandNotFoundException
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class MovementTest {

  private val movement = Movement()

  @Test
  internal fun `move forward`() {

    assertTrue { movement.move(coordinate = Coordinate(0, 0), command = 'f', orientation = N) == Coordinate(0, 1) }
    assertTrue { movement.move(coordinate = Coordinate(0, 0), command = 'f', orientation = E) == Coordinate(1, 0) }
    assertTrue { movement.move(coordinate = Coordinate(0, 0), command = 'f', orientation = S) == Coordinate(0, -1) }
    assertTrue { movement.move(coordinate = Coordinate(0, 0), command = 'f', orientation = W) == Coordinate(-1, 0) }
  }

  @Test
  internal fun `move backward`() {

    assertTrue { movement.move(coordinate = Coordinate(0, 0), command = 'b', orientation = N) == Coordinate(0, -1) }
    assertTrue { movement.move(coordinate = Coordinate(0, 0), command = 'b', orientation = E) == Coordinate(-1, 0) }
    assertTrue { movement.move(coordinate = Coordinate(0, 0), command = 'b', orientation = S) == Coordinate(0, 1) }
    assertTrue { movement.move(coordinate = Coordinate(0, 0), command = 'b', orientation = W) == Coordinate(1, 0) }
  }

  @Test
  internal fun `move forward over the edges`() {

    assertTrue { movement.move(mars, Coordinate(0, 4), 'f', N) == Coordinate(0, 0) }
    assertTrue { movement.move(mars, Coordinate(4, 0), 'f', E) == Coordinate(0, 0) }
    assertTrue { movement.move(mars, Coordinate(0, 0), 'f', S) == Coordinate(0, 4) }
    assertTrue { movement.move(mars, Coordinate(0, 0), 'f', W) == Coordinate(4, 0) }
  }

  @Test
  internal fun `move backward over the edges`() {

    assertTrue { movement.move(mars, Coordinate(0, 0), 'b', N) == Coordinate(0, 4) }
    assertTrue { movement.move(mars, Coordinate(0, 0), 'b', E) == Coordinate(4, 0) }
    assertTrue { movement.move(mars, Coordinate(0, 4), 'b', S) == Coordinate(0, 0) }
    assertTrue { movement.move(mars, Coordinate(4, 0), 'b', W) == Coordinate(0, 0) }
  }

  @Test
  internal fun `move command not found`() {

    assertThrows<CommandNotFoundException> {
      movement.move(coordinate = Coordinate(0, 0),
                    command = 'k',
                    orientation = N)
    }
  }

  companion object {

    private val mars = Mars(arrayOf(arrayOf(Empty, Empty, Empty, Rock, Empty),
                                    arrayOf(Empty, Rock, Empty, Empty, Empty),
                                    arrayOf(Empty, Empty, Empty, Empty, Empty),
                                    arrayOf(Empty, Empty, Empty, Empty, Rock),
                                    arrayOf(Empty, Rock, Rock, Empty, Empty)))
  }
}
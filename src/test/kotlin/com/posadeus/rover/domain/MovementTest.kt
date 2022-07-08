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

    assertTrue { movement.move(mars, Coordinate(2, 2), 'f', N) == Coordinate(2, 3) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'f', E) == Coordinate(3, 2) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'f', S) == Coordinate(2, 1) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'f', W) == Coordinate(1, 2) }
  }

  @Test
  internal fun `move backward`() {

    assertTrue { movement.move(mars, Coordinate(2, 2), 'b', N) == Coordinate(2, 1) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'b', E) == Coordinate(1, 2) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'b', S) == Coordinate(2, 3) }
    assertTrue { movement.move(mars, Coordinate(2, 2), 'b', W) == Coordinate(3, 2) }
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

    assertThrows<CommandNotFoundException> { movement.move(mars, Coordinate(0, 0), 'k', N) }
  }

  companion object {

    private val mars = Mars(arrayOf(arrayOf(Empty, Empty, Empty, Rock, Empty),
                                    arrayOf(Empty, Rock, Empty, Empty, Empty),
                                    arrayOf(Empty, Empty, Empty, Empty, Empty),
                                    arrayOf(Empty, Empty, Empty, Empty, Rock),
                                    arrayOf(Empty, Rock, Rock, Empty, Empty)))
  }
}
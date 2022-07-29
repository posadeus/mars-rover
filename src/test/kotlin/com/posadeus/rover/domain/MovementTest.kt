package com.posadeus.rover.domain

import arrow.core.Either
import com.posadeus.rover.domain.Orientation.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MovementTest {

  private val movement = Movement()

  @Test
  internal fun `move forward`() {

    assertTrue { movement.calculate(mars, Coordinate(2, 2), 'f', N) == Either.Right(Coordinate(2, 3)) }
    assertTrue { movement.calculate(mars, Coordinate(2, 2), 'f', E) == Either.Right(Coordinate(3, 2)) }
    assertTrue { movement.calculate(mars, Coordinate(2, 2), 'f', S) == Either.Right(Coordinate(2, 1)) }
    assertTrue { movement.calculate(mars, Coordinate(2, 2), 'f', W) == Either.Right(Coordinate(1, 2)) }
  }

  @Test
  internal fun `move backward`() {

    assertTrue { movement.calculate(mars, Coordinate(2, 2), 'b', N) == Either.Right(Coordinate(2, 1)) }
    assertTrue { movement.calculate(mars, Coordinate(2, 2), 'b', E) == Either.Right(Coordinate(1, 2)) }
    assertTrue { movement.calculate(mars, Coordinate(2, 2), 'b', S) == Either.Right(Coordinate(2, 3)) }
    assertTrue { movement.calculate(mars, Coordinate(2, 2), 'b', W) == Either.Right(Coordinate(3, 2)) }
  }

  @Test
  internal fun `move forward over the edges`() {

    assertTrue { movement.calculate(mars, Coordinate(0, 4), 'f', N) == Either.Right(Coordinate(0, 0)) }
    assertTrue { movement.calculate(mars, Coordinate(4, 0), 'f', E) == Either.Right(Coordinate(0, 0)) }
    assertTrue { movement.calculate(mars, Coordinate(0, 0), 'f', S) == Either.Right(Coordinate(0, 4)) }
    assertTrue { movement.calculate(mars, Coordinate(0, 0), 'f', W) == Either.Right(Coordinate(4, 0)) }
  }

  @Test
  internal fun `move backward over the edges`() {

    assertTrue { movement.calculate(mars, Coordinate(0, 0), 'b', N) == Either.Right(Coordinate(0, 4)) }
    assertTrue { movement.calculate(mars, Coordinate(0, 0), 'b', E) == Either.Right(Coordinate(4, 0)) }
    assertTrue { movement.calculate(mars, Coordinate(0, 4), 'b', S) == Either.Right(Coordinate(0, 0)) }
    assertTrue { movement.calculate(mars, Coordinate(4, 0), 'b', W) == Either.Right(Coordinate(0, 0)) }
  }

  @Test
  internal fun `move command not found`() {

    assertTrue { movement.calculate(mars, Coordinate(0, 0), 'k', N) == Either.Left(CommandNotFound) }
  }

  @Test
  internal fun `impossible movement for obstacle detected`() {

    val error = Either.Left(MissionAborted("Obstacle found: ${Coordinate(1, 1)}"))

    assertTrue { movement.calculate(mars, Coordinate(0, 1), 'f', E) == error }
  }

  companion object {

    private val mars = Mars(arrayOf(arrayOf(Empty, Empty, Empty, Rock, Empty),
                                    arrayOf(Empty, Rock, Empty, Empty, Empty),
                                    arrayOf(Empty, Empty, Empty, Empty, Empty),
                                    arrayOf(Empty, Empty, Empty, Empty, Rock),
                                    arrayOf(Empty, Rock, Rock, Empty, Empty)))
  }
}
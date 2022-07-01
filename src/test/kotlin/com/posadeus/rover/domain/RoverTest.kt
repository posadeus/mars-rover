package com.posadeus.rover.domain

import com.posadeus.rover.domain.exception.WrongCoordinateException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

class RoverTest {

  @Test
  internal fun `rover initial position`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    val rover = Rover(mars, Coordinate(0, 0), "N")

    assertTrue { rover.position() == Coordinate(0, 0) }
    assertTrue { rover.orientation == "N" }
  }

  @Test
  internal fun `rover wrong initial position`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    val rover = Rover(mars, Coordinate(-3, 0), "N")

    assertThrows<WrongCoordinateException> { rover.position() }
  }

  @Test
  internal fun `move forward on Y`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), "N")

    assertTrue { rover.forward() == Coordinate(0, 1) }
  }

  @Test
  internal fun `move forward on X`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), "E")

    assertTrue { rover.forward() == Coordinate(1, 0) }
  }

  @Test
  internal fun `move backward on Y`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), "N")

    assertTrue { rover.backward() == Coordinate(0, -1) }
  }
}
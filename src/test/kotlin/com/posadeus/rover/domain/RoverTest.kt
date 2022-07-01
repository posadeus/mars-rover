package com.posadeus.rover.domain

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class RoverTest {

  @Test
  internal fun `rover initial position`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    val rover = Rover(mars, Coordinate(0, 0), "N")

    assertTrue { rover.position() == Pair(-2, -2) }
    assertTrue { rover.orientation == "N" }
  }

  @Test
  internal fun `move forward on Y`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), "N")

    assertTrue { rover.forward() == Pair(-2, -1) }
  }

  @Test
  internal fun `move forward on X`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), "E")

    assertTrue { rover.forward() == Pair(-1, -2) }
  }
}
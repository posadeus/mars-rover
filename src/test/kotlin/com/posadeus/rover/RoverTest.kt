package com.posadeus.rover

import com.posadeus.mars.Mars
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class RoverTest {

  @Test
  internal fun `rover initial position`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, 0, 0, "N")

    assertTrue { rover.position() == mars.coordinate(0, 0) }
    assertTrue { rover.orientation == "N" }
  }
}
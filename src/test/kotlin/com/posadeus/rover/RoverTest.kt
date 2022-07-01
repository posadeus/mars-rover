package com.posadeus.rover

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class RoverTest {

  @Test
  internal fun `rover initial position`() {

    val rover = Rover(0, 0)

    assertTrue { rover.position() == Pair(0, 0) }
  }
}
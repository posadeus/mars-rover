package com.posadeus.rover.domain

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ObstacleTest {

  @Test
  internal fun `obstacle detection`() {

    assertTrue { Obstacle(Coordinate(-2, 0)).isPresent(Coordinate(-2, 0)) }
  }
}
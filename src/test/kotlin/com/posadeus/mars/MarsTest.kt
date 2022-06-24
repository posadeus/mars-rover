package com.posadeus.mars

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class MarsTest {

  @Test
  internal fun `define Mars surface`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    assertTrue { mars.coordinate(1, 1) == Pair(-1, -1) }
  }
}
package com.posadeus.rover.domain

import com.posadeus.rover.domain.Orientation.*
import com.posadeus.rover.domain.exception.CommandNotFoundException
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TurnTest {

  private val turn = Turn()

  @Test
  internal fun `turn right`() {

    assertTrue { turn.turn('r', N) == E }
    assertTrue { turn.turn('r', E) == S }
    assertTrue { turn.turn('r', S) == W }
    assertTrue { turn.turn('r', W) == N }
  }

  @Test
  internal fun `turn left`() {

    assertTrue { turn.turn('l', N) == W }
    assertTrue { turn.turn('l', E) == N }
    assertTrue { turn.turn('l', S) == E }
    assertTrue { turn.turn('l', W) == S }
  }

  @Test
  internal fun `turn command not found`() {

    assertThrows<CommandNotFoundException> { turn.turn('k', N) }
  }
}
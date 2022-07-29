package com.posadeus.rover.domain

import arrow.core.Either
import com.posadeus.rover.domain.Orientation.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class TurnTest {

  private val turn = Turn()

  @Test
  internal fun `turn right`() {

    assertTrue { turn.turn('r', N) == Either.Right(E) }
    assertTrue { turn.turn('r', E) == Either.Right(S) }
    assertTrue { turn.turn('r', S) == Either.Right(W) }
    assertTrue { turn.turn('r', W) == Either.Right(N) }
  }

  @Test
  internal fun `turn left`() {

    assertTrue { turn.turn('l', N) == Either.Right(W) }
    assertTrue { turn.turn('l', E) == Either.Right(N) }
    assertTrue { turn.turn('l', S) == Either.Right(E) }
    assertTrue { turn.turn('l', W) == Either.Right(S) }
  }

  @Test
  internal fun `turn command not found`() {

    assertTrue { turn.turn('k', N) == Either.Left(CommandNotFound("Command not found 'k'")) }
  }
}
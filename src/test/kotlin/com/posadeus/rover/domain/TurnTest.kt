package com.posadeus.rover.domain

import arrow.core.Either
import com.posadeus.rover.domain.Orientation.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class TurnTest {

  private val turn = Turn()

  @Test
  internal fun `turn right`() {

    assertTrue { turn.calculate('r', N) == Either.Right(E) }
    assertTrue { turn.calculate('r', E) == Either.Right(S) }
    assertTrue { turn.calculate('r', S) == Either.Right(W) }
    assertTrue { turn.calculate('r', W) == Either.Right(N) }
  }

  @Test
  internal fun `turn left`() {

    assertTrue { turn.calculate('l', N) == Either.Right(W) }
    assertTrue { turn.calculate('l', E) == Either.Right(N) }
    assertTrue { turn.calculate('l', S) == Either.Right(E) }
    assertTrue { turn.calculate('l', W) == Either.Right(S) }
  }

  @Test
  internal fun `turn command not found`() {

    assertTrue { turn.calculate('k', N) == Either.Left(CommandNotFound("Command not found 'k'")) }
  }
}
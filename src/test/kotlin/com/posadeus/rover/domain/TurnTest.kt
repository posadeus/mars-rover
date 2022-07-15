package com.posadeus.rover.domain

import arrow.core.getOrElse
import com.posadeus.rover.domain.Orientation.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class TurnTest {

  private val turn = Turn()

  @Test
  internal fun `turn right`() {

    assertTrue { turn.turn('r', N).getOrElse { null } == E }
    assertTrue { turn.turn('r', E).getOrElse { null } == S }
    assertTrue { turn.turn('r', S).getOrElse { null } == W }
    assertTrue { turn.turn('r', W).getOrElse { null } == N }
  }

  @Test
  internal fun `turn left`() {

    assertTrue { turn.turn('l', N).getOrElse { null } == W }
    assertTrue { turn.turn('l', E).getOrElse { null } == N }
    assertTrue { turn.turn('l', S).getOrElse { null } == E }
    assertTrue { turn.turn('l', W).getOrElse { null } == S }
  }

  @Test
  internal fun `turn command not found`() {

    val turns = turn.turn('k', N)

    assertTrue { turns.isLeft() }
    assertTrue { turns.fold({ it }, { it }) == CommandNotFound }
  }
}
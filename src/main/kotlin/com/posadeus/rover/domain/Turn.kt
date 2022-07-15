package com.posadeus.rover.domain

import arrow.core.Either
import com.posadeus.rover.domain.Orientation.*

class Turn {

  fun turn(command: Char,
           orientation: Orientation): Either<Error, Orientation> =
      when (command) {
        'r' -> Either.Right(turnRight(orientation))
        'l' -> Either.Right(turnLeft(orientation))
        else -> Either.Left(CommandNotFound)
      }

  private fun turnRight(orientation: Orientation): Orientation =
      when (orientation) {
        N -> E
        S -> W
        E -> S
        W -> N
      }

  private fun turnLeft(orientation: Orientation): Orientation =
      when (orientation) {
        N -> W
        S -> E
        E -> N
        W -> S
      }
}

package com.posadeus.rover.domain

import com.posadeus.rover.domain.Orientation.*
import com.posadeus.rover.domain.exception.CommandNotFoundException

class Turn {

  fun turn(command: Char, orientation: Orientation): Orientation =
      when (command) {
        'r' -> turnRight(orientation)
        'l' -> turnLeft(orientation)
        else -> throw CommandNotFoundException()
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

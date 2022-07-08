package com.posadeus.rover.domain

import com.posadeus.rover.domain.exception.CommandNotFoundException
import com.posadeus.rover.domain.exception.WrongCoordinateException

data class CommandCenter(private val mars: Mars,
                         private val rover: Rover,
                         private val movement: Movement,
                         private val turn: Turn) {

  fun position(): Coordinate =
      if (mars.isValidCoordinate(rover.coordinate))
        rover.coordinate
      else
        throw WrongCoordinateException()

  fun orientation(): Orientation =
      rover.orientation

  fun execute(commands: Array<Char>): Rover {

    tailrec fun go(commands: Iterator<Char>, rover: Rover): Rover =
        if (commands.hasNext()) {

          when (val command = commands.next()) {
            'f', 'b' -> go(commands, move(movement.move(rover.coordinate, command, rover.orientation), rover))
            'r', 'l' -> go(commands, turn(turn.turn(command, rover.orientation), rover))
            else -> throw CommandNotFoundException()
          }
        }
        else
          rover

    return go(commands.iterator(), rover)
  }

  private fun move(coordinate: Coordinate, rover: Rover): Rover =
      Rover(coordinate, rover.orientation)

  private fun turn(orientation: Orientation, rover: Rover): Rover =
      Rover(rover.coordinate, orientation)
}

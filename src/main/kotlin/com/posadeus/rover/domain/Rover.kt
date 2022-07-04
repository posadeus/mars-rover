package com.posadeus.rover.domain

import com.posadeus.rover.domain.exception.CommandNotFoundException
import com.posadeus.rover.domain.exception.WrongCoordinateException

data class Rover(private val mars: Mars,
                 private val coordinate: Coordinate,
                 val orientation: Orientation,
                 val movement: Movement,
                 val turn: Turn) {

  fun position(): Coordinate =
      if (mars.isValidCoordinate(coordinate))
        coordinate
      else
        throw WrongCoordinateException()

  fun execute(commands: Array<Char>): Rover {

    tailrec fun go(commands: Iterator<Char>, rover: Rover): Rover {

      return if (commands.hasNext()) {

        when (val command = commands.next()) {
          'f', 'b' -> go(commands, move(rover.movement.move(rover.coordinate, command, rover.orientation), rover))
          'r', 'l' -> go(commands, turn(rover.turn.turn(command, rover.orientation), rover))
          else -> throw CommandNotFoundException()
        }
      }
      else
        rover
    }

    return go(commands.iterator(), this)
  }

  private fun move(coordinate: Coordinate, rover: Rover): Rover =
      Rover(rover.mars, coordinate, rover.orientation, rover.movement, rover.turn)

  private fun turn(orientation: Orientation, rover: Rover): Rover =
      Rover(rover.mars, rover.coordinate, orientation, rover.movement, rover.turn)
}

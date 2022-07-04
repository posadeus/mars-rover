package com.posadeus.rover.domain

import com.posadeus.rover.domain.exception.CommandNotFoundException
import com.posadeus.rover.domain.exception.WrongCoordinateException

data class CommandCenter(private val mars: Mars,
                         private val coordinate: Coordinate,
                         val orientation: Orientation,
                         val movement: Movement,
                         val turn: Turn) {

  fun position(): Coordinate =
      if (mars.isValidCoordinate(coordinate))
        coordinate
      else
        throw WrongCoordinateException()

  fun execute(commands: Array<Char>): CommandCenter {

    tailrec fun go(commands: Iterator<Char>, commandCenter: CommandCenter): CommandCenter {

      return if (commands.hasNext()) {

        when (val command = commands.next()) {
          'f', 'b' -> go(commands,
                         move(commandCenter.movement.move(commandCenter.coordinate, command, commandCenter.orientation),
                              commandCenter))
          'r', 'l' -> go(commands, turn(commandCenter.turn.turn(command, commandCenter.orientation), commandCenter))
          else -> throw CommandNotFoundException()
        }
      }
      else
        commandCenter
    }

    return go(commands.iterator(), this)
  }

  private fun move(coordinate: Coordinate, commandCenter: CommandCenter): CommandCenter =
      CommandCenter(commandCenter.mars,
                    coordinate,
                    commandCenter.orientation,
                    commandCenter.movement,
                    commandCenter.turn)

  private fun turn(orientation: Orientation, commandCenter: CommandCenter): CommandCenter =
      CommandCenter(commandCenter.mars,
                    commandCenter.coordinate,
                    orientation,
                    commandCenter.movement,
                    commandCenter.turn)
}

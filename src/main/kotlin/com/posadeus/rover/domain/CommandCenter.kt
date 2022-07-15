package com.posadeus.rover.domain

import com.posadeus.rover.domain.exception.CommandNotFoundException
import com.posadeus.rover.domain.exception.MissionAbortedException
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

    fun go(commands: Iterator<Char>, rover: Rover): Rover =
        if (commands.hasNext()) {

          when (val command = commands.next()) {
            'f', 'b' ->
              try {
                go(commands, move(abortIfObstacledOrUpdateCoordinates(rover, command), rover))
              }
              catch (e: MissionAbortedException) {
                rover
              }

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

  private fun abortIfObstacledOrUpdateCoordinates(rover: Rover, command: Char): Coordinate {

    val newCoordinate = movement.move(mars,
                                      rover.coordinate,
                                      command,
                                      rover.orientation)

    return if (mars.hasObstacle(newCoordinate)) throw MissionAbortedException()
    else newCoordinate
  }
}

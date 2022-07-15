package com.posadeus.rover.domain

import arrow.core.Either

data class CommandCenter(private val mars: Mars,
                         private val rover: Rover,
                         private val movement: Movement,
                         private val turn: Turn) {

  fun position(): Either<Error, Coordinate> =
      if (mars.isValidCoordinate(rover.coordinate))
        Either.Right(rover.coordinate)
      else
        Either.Left(WrongCoordinate("Coordinate not allowed: ${rover.coordinate}"))

  fun orientation(): Orientation =
      rover.orientation

  fun execute(commands: Array<Char>): Either<Error, Rover> {

    fun go(commands: Iterator<Char>, rover: Rover): Either<Error, Rover> =
        if (commands.hasNext()) {

          when (val command = commands.next()) {
            'f', 'b' ->
              abortIfObstacledOrUpdateCoordinates(movement.move(mars,
                                                                rover.coordinate,
                                                                command,
                                                                rover.orientation))
                  .fold({ Either.Right(rover) }, // FIXME the error from movement is never reported
                        { go(commands, move(it, rover)) })
            'r', 'l' -> turn.turn(command,
                                  rover.orientation).fold({ Either.Right(rover) }, // FIXME the error from turn is never reported
                                                          { go(commands, turn(it, rover)) })
            else -> Either.Left(CommandNotFound)
          }
        }
        else
          Either.Right(rover)

    return go(commands.iterator(), rover)
  }

  private fun move(coordinate: Coordinate, rover: Rover): Rover =
      Rover(coordinate, rover.orientation)

  private fun turn(orientation: Orientation, rover: Rover): Rover =
      Rover(rover.coordinate, orientation)

  private fun abortIfObstacledOrUpdateCoordinates(newCoordinate: Either<Error, Coordinate>): Either<Error, Coordinate> {

    return if (newCoordinate.exists { mars.hasObstacle(it) })
      Either.Left(MissionAborted("Obstacle found: $newCoordinate"))
    else
      newCoordinate
  }
}

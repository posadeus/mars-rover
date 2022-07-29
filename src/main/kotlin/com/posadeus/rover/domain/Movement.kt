package com.posadeus.rover.domain

import arrow.core.Either
import arrow.core.flatMap
import com.posadeus.rover.domain.Orientation.*

class Movement {

  fun calculate(mars: Mars,
                coordinate: Coordinate,
                command: Char,
                orientation: Orientation): Either<Error, Coordinate> =
      when (command) {
        'f' -> abortIfObstacledOrUpdateCoordinate(forward(mars, coordinate, orientation), mars)
        'b' -> abortIfObstacledOrUpdateCoordinate(backward(mars, coordinate, orientation), mars)
        else -> Either.Left(CommandNotFound("Command not found '$command'"))
      }

  private fun forward(mars: Mars,
                      coordinate: Coordinate,
                      orientation: Orientation): Coordinate =
      when (orientation) {
        N -> nextCoordinate(mars = mars,
                            nextInsideCoordinate = Coordinate(coordinate.x, coordinate.y + 1),
                            pacmanCoordinate = Coordinate(coordinate.x, 0))
        S -> nextCoordinate(mars = mars,
                            nextInsideCoordinate = Coordinate(coordinate.x, coordinate.y - 1),
                            pacmanCoordinate = Coordinate(coordinate.x, mars.coordinates[coordinate.x].size - 1))
        E -> nextCoordinate(mars = mars,
                            nextInsideCoordinate = Coordinate(coordinate.x + 1, coordinate.y),
                            pacmanCoordinate = Coordinate(0, coordinate.y))
        W -> nextCoordinate(mars = mars,
                            nextInsideCoordinate = Coordinate(coordinate.x - 1, coordinate.y),
                            pacmanCoordinate = Coordinate(mars.coordinates[coordinate.x].size - 1, coordinate.y))
      }

  private fun backward(mars: Mars,
                       coordinate: Coordinate,
                       orientation: Orientation): Coordinate =
      when (orientation) {
        N -> forward(mars, coordinate, S)
        S -> forward(mars, coordinate, N)
        E -> forward(mars, coordinate, W)
        W -> forward(mars, coordinate, E)
      }

  private fun nextCoordinate(mars: Mars,
                             nextInsideCoordinate: Coordinate,
                             pacmanCoordinate: Coordinate): Coordinate =
      isMovingInsideEdges(mars, nextInsideCoordinate)
          .fold({ pacmanCoordinate },
                { nextInsideCoordinate })

  private fun isMovingInsideEdges(mars: Mars,
                                  nextInsideCoordinate: Coordinate): Either<Error, Boolean> =
      mars.isValidCoordinate(nextInsideCoordinate)

  private fun abortIfObstacledOrUpdateCoordinate(newCoordinate: Coordinate,
                                                 mars: Mars): Either<Error, Coordinate> =
      mars.hasObstacle(newCoordinate)
          .flatMap {
            if (it)
              Either.Left(MissionAborted("Obstacle found: $newCoordinate"))
            else
              Either.Right(newCoordinate)
          }
}

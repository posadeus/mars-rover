package com.posadeus.rover.domain

import arrow.core.Either
import com.posadeus.rover.domain.Orientation.*

class Movement {

  fun calculate(mars: Mars,
                coordinate: Coordinate,
                command: Char,
                orientation: Orientation): Either<Error, Coordinate> =
      when (command) {
        'f' -> Either.Right(forward(mars, coordinate, orientation))
        'b' -> Either.Right(backward(mars, coordinate, orientation))
        else -> Either.Left(CommandNotFound)
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
      if (isMovingInsideEdges(mars, nextInsideCoordinate)) nextInsideCoordinate
      else pacmanCoordinate

  private fun isMovingInsideEdges(mars: Mars,
                                  nextInsideCoordinate: Coordinate): Boolean =
      mars.isValidCoordinate(nextInsideCoordinate)
}

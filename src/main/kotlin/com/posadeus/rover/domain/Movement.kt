package com.posadeus.rover.domain

import arrow.core.Either
import com.posadeus.rover.domain.Orientation.*

class Movement {

  fun move(mars: Mars,
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
        N -> move(mars = mars,
                  nextInsideCoordinate = Coordinate(coordinate.x, coordinate.y + 1),
                  pacmanPoint = Coordinate(coordinate.x, 0))
        S -> move(mars = mars,
                  nextInsideCoordinate = Coordinate(coordinate.x, coordinate.y - 1),
                  pacmanPoint = Coordinate(coordinate.x, mars.coordinates[coordinate.x].size - 1))
        E -> move(mars = mars,
                  nextInsideCoordinate = Coordinate(coordinate.x + 1, coordinate.y),
                  pacmanPoint = Coordinate(0, coordinate.y))
        W -> move(mars = mars,
                  nextInsideCoordinate = Coordinate(coordinate.x - 1, coordinate.y),
                  pacmanPoint = Coordinate(mars.coordinates[coordinate.x].size - 1, coordinate.y))
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

  private fun move(mars: Mars,
                   nextInsideCoordinate: Coordinate,
                   pacmanPoint: Coordinate): Coordinate =
      if (isMovingInsideEdges(mars, nextInsideCoordinate)) nextInsideCoordinate
      else pacmanPoint

  private fun isMovingInsideEdges(mars: Mars,
                                  nextInsideCoordinate: Coordinate): Boolean =
      mars.isValidCoordinate(nextInsideCoordinate)
}

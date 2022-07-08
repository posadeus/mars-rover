package com.posadeus.rover.domain

import com.posadeus.rover.domain.Orientation.*
import com.posadeus.rover.domain.exception.CommandNotFoundException

class Movement {

  fun move(mars: Mars,
           coordinate: Coordinate,
           command: Char,
           orientation: Orientation): Coordinate =
      when (command) {
        'f' -> forward(mars, coordinate, orientation)
        'b' -> backward(mars, coordinate, orientation)
        else -> throw CommandNotFoundException()
      }

  private fun forward(mars: Mars,
                      coordinate: Coordinate,
                      orientation: Orientation): Coordinate =
      when (orientation) {
        N -> Coordinate(coordinate.x,
                        move(isMovingInsideEdges(mars, coordinate) { Coordinate(coordinate.x, coordinate.y + 1) },
                             coordinate.y + 1,
                             0))
        S -> Coordinate(coordinate.x,
                        move(isMovingInsideEdges(mars, coordinate) { Coordinate(coordinate.x, coordinate.y - 1) },
                             coordinate.y - 1,
                             mars.coordinates[coordinate.y].size - 1))
        E -> Coordinate(move(isMovingInsideEdges(mars, coordinate) { Coordinate(coordinate.x + 1, coordinate.y) },
                             coordinate.x + 1,
                             0),
                        coordinate.y)
        W -> Coordinate(move(isMovingInsideEdges(mars, coordinate) { Coordinate(coordinate.x - 1, coordinate.y) },
                             coordinate.x - 1,
                             mars.coordinates[coordinate.x].size - 1),
                        coordinate.y)
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

  private fun move(isMoveInsideEdges: Boolean,
                   insidePoint: Int,
                   pacmanPoint: Int) =
      if (isMoveInsideEdges) insidePoint
      else pacmanPoint

  private fun isMovingInsideEdges(mars: Mars,
                                  coordinate: Coordinate,
                                  nextCoordinateFunc: (Coordinate) -> Coordinate): Boolean =
      mars.isValidCoordinate(nextCoordinateFunc(coordinate))
}

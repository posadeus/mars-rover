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
                        possibleMoveOverTheEdge(isMoveInsideEdges(mars, coordinate)
                                                { Coordinate(coordinate.x, coordinate.y + 1) },
                                                coordinate.y + 1,
                                                0))
        S -> Coordinate(coordinate.x,
                        possibleMoveOverTheEdge(isMoveInsideEdges(mars, coordinate)
                                                { Coordinate(coordinate.x, coordinate.y - 1) },
                                                coordinate.y - 1,
                                                4))
        E -> Coordinate(possibleMoveOverTheEdge(isMoveInsideEdges(mars, coordinate)
                                                { Coordinate(coordinate.x + 1, coordinate.y) },
                                                coordinate.x + 1,
                                                0),
                        coordinate.y)
        W -> Coordinate(possibleMoveOverTheEdge(isMoveInsideEdges(mars, coordinate)
                                                { Coordinate(coordinate.x - 1, coordinate.y) },
                                                coordinate.x - 1,
                                                4),
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

  private fun possibleMoveOverTheEdge(isMoveInsideEdges: Boolean,
                                      insidePoint: Int,
                                      pacmanPoint: Int) =
      if (isMoveInsideEdges) insidePoint
      else pacmanPoint

  private fun isMoveInsideEdges(mars: Mars,
                                coordinate: Coordinate,
                                nextCoordinateFunc: (Coordinate) -> Coordinate): Boolean =
      mars.isValidCoordinate(nextCoordinateFunc(coordinate))
}

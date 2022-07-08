package com.posadeus.rover.domain

import com.posadeus.rover.domain.Orientation.*
import com.posadeus.rover.domain.exception.CommandNotFoundException

class Movement {

  fun move(mars: Mars? = null,
           coordinate: Coordinate,
           command: Char,
           orientation: Orientation): Coordinate =
      when (command) {
        'f' -> forward(mars, coordinate, orientation)
        'b' -> backward(mars, coordinate, orientation)
        else -> throw CommandNotFoundException()
      }

  private fun forward(mars: Mars?,
                      coordinate: Coordinate,
                      orientation: Orientation): Coordinate =
      when (orientation) {
        N -> Coordinate(coordinate.x,
                        movementWithPossiblePacManEffect(mars, coordinate, coordinate.y + 1, 0)
                        { Coordinate(coordinate.x, coordinate.y + 1) })
        S -> Coordinate(coordinate.x,
                        movementWithPossiblePacManEffect(mars, coordinate, coordinate.y - 1, 4)
                        { Coordinate(coordinate.x, coordinate.y - 1) })
        E -> Coordinate(movementWithPossiblePacManEffect(mars, coordinate, coordinate.x + 1, 0)
                        { Coordinate(coordinate.x + 1, coordinate.y) },
                        coordinate.y)
        W -> Coordinate(movementWithPossiblePacManEffect(mars, coordinate, coordinate.x - 1, 4)
                        { Coordinate(coordinate.x - 1, coordinate.y) },
                        coordinate.y)
      }

  private fun backward(mars: Mars?,
                       coordinate: Coordinate,
                       orientation: Orientation): Coordinate =
      when (orientation) {
        N -> forward(mars, coordinate, S)
        S -> forward(mars, coordinate, N)
        E -> forward(mars, coordinate, W)
        W -> forward(mars, coordinate, E)
      }

  private fun movementWithPossiblePacManEffect(mars: Mars?,
                                               coordinate: Coordinate,
                                               newPoint: Int,
                                               pacmanPoint: Int,
                                               movementFunc: (Coordinate) -> Coordinate) =
      if (isValidMovement(mars, coordinate, movementFunc))
        newPoint
      else
        pacmanPoint

  private fun isValidMovement(mars: Mars?, coordinate: Coordinate, func: (Coordinate) -> Coordinate): Boolean =
      mars?.isValidCoordinate(func(coordinate)) ?: true
}

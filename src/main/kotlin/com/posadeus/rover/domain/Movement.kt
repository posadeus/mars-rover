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
        'b' -> backward(coordinate, orientation)
        else -> throw CommandNotFoundException()
      }

  private fun forward(mars: Mars?,
                      coordinate: Coordinate,
                      orientation: Orientation): Coordinate =
      when (orientation) {
        N -> Coordinate(coordinate.x,
                        if (isValidMovement(mars, coordinate) { Coordinate(coordinate.x, coordinate.y + 1) })
                          coordinate.y + 1
                        else
                          0)
        S -> Coordinate(coordinate.x,
                        if (isValidMovement(mars, coordinate) { Coordinate(coordinate.x, coordinate.y - 1) })
                          coordinate.y - 1
                        else
                          4)
        E -> Coordinate(coordinate.x + 1, coordinate.y)
        W -> Coordinate(coordinate.x - 1, coordinate.y)
      }

  private fun backward(coordinate: Coordinate,
                       orientation: Orientation): Coordinate =
      when (orientation) {
        N -> Coordinate(coordinate.x, coordinate.y - 1)
        S -> Coordinate(coordinate.x, coordinate.y + 1)
        E -> Coordinate(coordinate.x - 1, coordinate.y)
        W -> Coordinate(coordinate.x + 1, coordinate.y)
      }

  private fun isValidMovement(mars: Mars?, coordinate: Coordinate, func: (Coordinate) -> Coordinate): Boolean =
      mars?.isValidCoordinate(func(coordinate)) ?: true
}

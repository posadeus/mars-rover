package com.posadeus.rover.domain

import com.posadeus.rover.domain.Orientation.*
import com.posadeus.rover.domain.exception.WrongCoordinateException

data class Rover(private val mars: Mars,
                 private val coordinate: Coordinate,
                 val orientation: Orientation) {

  fun position(): Coordinate =
      if (mars.isValidCoordinate(coordinate))
        coordinate
      else
        throw WrongCoordinateException()

  fun forward(): Coordinate =
      when (orientation) {
        N -> Coordinate(coordinate.x, coordinate.y + 1)
        S -> Coordinate(coordinate.x, coordinate.y - 1)
        E -> Coordinate(coordinate.x + 1, coordinate.y)
        W -> Coordinate(coordinate.x - 1, coordinate.y)
      }

  fun backward(): Coordinate =
      when (orientation) {
        N -> Coordinate(coordinate.x, coordinate.y - 1)
        S -> Coordinate(coordinate.x, coordinate.y + 1)
        E -> Coordinate(coordinate.x - 1, coordinate.y)
        W -> Coordinate(coordinate.x + 1, coordinate.y)
      }

  fun move(movement: Char): Rover =
      Rover(mars,
            when (movement) {
              'f' -> forward()
              else -> coordinate
            },
            orientation)
}

package com.posadeus.rover.domain

import com.posadeus.rover.domain.exception.WrongCoordinateException

class Rover(private val mars: Mars,
            private val coordinate: Coordinate,
            val orientation: String) {

  fun position(): Coordinate =
      if (mars.isValidCoordinate(coordinate))
        coordinate
      else
        throw WrongCoordinateException()

  fun forward(): Coordinate =
      if (orientation == "N") Coordinate(coordinate.x, coordinate.y + 1)
      else Coordinate(coordinate.x + 1, coordinate.y)
}

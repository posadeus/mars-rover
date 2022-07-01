package com.posadeus.rover.domain

import com.posadeus.rover.domain.exception.WrongCoordinateException

class Rover(private val mars: Mars,
            private val coordinate: Coordinate,
            val orientation: String) {

  fun position(): Coordinate =
      if (mars.isValidCoordinate(coordinate.x, coordinate.y))
        coordinate
      else
        throw WrongCoordinateException()

  fun forward(): Coordinate =
      if (orientation == "N") mars.coordinate(coordinate.x, coordinate.y + 1)
      else mars.coordinate(coordinate.x + 1, coordinate.y)
}

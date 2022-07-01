package com.posadeus.rover.domain

class Rover(private val mars: Mars,
            private val coordinate: Coordinate,
            val orientation: String) {

  fun position(): Pair<Int, Int> =
      mars.coordinate(coordinate.x, coordinate.y)

  fun forward(): Pair<Int, Int> =
      if (orientation == "N") mars.coordinate(coordinate.x, coordinate.y + 1)
      else mars.coordinate(coordinate.x + 1, coordinate.y)
}

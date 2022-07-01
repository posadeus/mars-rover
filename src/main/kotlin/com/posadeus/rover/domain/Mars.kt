package com.posadeus.rover.domain

class Mars(private val x: Array<Int>,
           private val y: Array<Int>) {

  @Deprecated("no more needed")
  fun coordinate(x: Int, y: Int): Coordinate =
      Coordinate(this.x[x], this.y[y])

  fun isValidCoordinate(coordinate: Coordinate): Boolean =
      this.x.any { it == coordinate.x } && this.y.any { it == coordinate.y }
}

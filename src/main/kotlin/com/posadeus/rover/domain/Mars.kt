package com.posadeus.rover.domain

class Mars(private val x: Array<Int>,
           private val y: Array<Int>) {

  @Deprecated("no more needed")
  fun coordinate(x: Int, y: Int): Coordinate =
      Coordinate(this.x[x], this.y[y])

  fun isValidCoordinate(x: Int, y: Int): Boolean =
      this.x.any { it == x } && this.y.any { it == y }
}

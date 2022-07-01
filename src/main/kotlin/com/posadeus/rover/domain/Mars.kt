package com.posadeus.rover.domain

class Mars(private val x: Array<Int>,
           private val y: Array<Int>) {

  fun coordinate(x: Int, y: Int): Coordinate =
      Coordinate(this.x[x], this.y[y])
}

package com.posadeus.rover.domain

class Mars(private val x: Array<Int>,
           private val y: Array<Int>) {

  fun isValidCoordinate(coordinate: Coordinate): Boolean =
      this.x.any { it == coordinate.x } && this.y.any { it == coordinate.y }
}

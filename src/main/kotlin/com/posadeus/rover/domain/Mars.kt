package com.posadeus.rover.domain

class Mars(private val x: Array<Int>,
           private val y: Array<Int>,
           private val obstacles: Array<Obstacle> = emptyArray()) {

  fun isValidCoordinate(coordinate: Coordinate): Boolean =
      this.x.any { it == coordinate.x } && this.y.any { it == coordinate.y }

  fun hasObstacle(coordinate: Coordinate): Boolean =
      obstacles.any { it.isPresent(coordinate) }
}

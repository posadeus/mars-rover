package com.posadeus.rover.domain

class Mars(@Deprecated("use coordinates") private val x: Array<Int>,
           @Deprecated("use coordinates") private val y: Array<Int>,
           private val coordinates: Array<Array<Obstacle?>> = emptyArray()) {

  fun isValidCoordinate(coordinate: Coordinate): Boolean =
      coordinate.x >= 0
      && coordinate.y >= 0
      && coordinates.size >= coordinate.x
      && coordinates[coordinate.x].size >= coordinate.y

  fun hasObstacle(coordinate: Coordinate): Boolean =
      when (coordinates[coordinate.x][coordinate.y]) {
        is Obstacle -> true
        else -> false
      }
}

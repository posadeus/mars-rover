package com.posadeus.rover.domain

class Mars(@Deprecated("use coordinates") private val x: Array<Int>,
           @Deprecated("use coordinates") private val y: Array<Int>,
           @Deprecated("use coordinates") private val obstacles: Array<Obstacle> = emptyArray(),
           private val coordinates: Array<Array<Obstacle?>> = emptyArray()) {

  fun isValidCoordinateNew(coordinate: Coordinate): Boolean =
      coordinate.x >= 0
      && coordinate.y >= 0
      && coordinates.size >= coordinate.x
      && coordinates[coordinate.x].size >= coordinate.y

  fun hasObstacleNew(coordinate: Coordinate): Boolean =
      when (coordinates[coordinate.x][coordinate.y]) {
        is Obstacle -> true
        else -> false
      }
}

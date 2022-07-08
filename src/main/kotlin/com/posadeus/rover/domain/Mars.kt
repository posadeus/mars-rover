package com.posadeus.rover.domain

class Mars(private val coordinates: Array<Array<Obstacle?>>) {

  fun isValidCoordinate(coordinate: Coordinate): Boolean =
      coordinate.x >= 0
      && coordinate.y >= 0
      && coordinates.size >= coordinate.x
      && coordinates[coordinate.x].size >= coordinate.y

  fun hasObstacle(coordinate: Coordinate): Boolean =
      isValidCoordinate(coordinate)
      && coordinates[coordinate.x][coordinate.y]!!.hasObstacle()
}

package com.posadeus.rover.domain

class Obstacle(val coordinate: Coordinate) {

  fun isPresent(coordinate: Coordinate): Boolean =
      this.coordinate == coordinate
}

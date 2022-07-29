package com.posadeus.rover.domain

import arrow.core.Either

class Mars(val coordinates: Array<Array<Obstacle?>>) {

  private val validCoordinateCondition: (Coordinate) -> Boolean =
      {
        it.x >= 0
        && it.y >= 0
        && coordinates.size > it.x
        && coordinates[it.x].size > it.y
      }

  @Deprecated("Use isValidCoordinateNew instead")
  fun isValidCoordinate(coordinate: Coordinate): Boolean =
      validCoordinateCondition(coordinate)

  fun isValidCoordinateNew(coordinate: Coordinate): Either<Error, Boolean> =
      Either.conditionally(validCoordinateCondition(coordinate),
                           { WrongCoordinate("Coordinate not allowed: $coordinate") },
                           { true })

  fun hasObstacle(coordinate: Coordinate): Boolean =
      isValidCoordinate(coordinate)
      && coordinates[coordinate.x][coordinate.y]!!.hasObstacle()
}

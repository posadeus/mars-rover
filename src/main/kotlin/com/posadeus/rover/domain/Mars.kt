package com.posadeus.rover.domain

import arrow.core.Either
import arrow.core.continuations.either

class Mars(val coordinates: Array<Array<Obstacle>>) {

  private val validCoordinateCondition: (Coordinate) -> Boolean =
      {
        it.x >= 0
        && it.y >= 0
        && coordinates.size > it.x
        && coordinates[it.x].size > it.y
      }

  fun isValidCoordinate(coordinate: Coordinate): Either<Error, Boolean> =
      Either.conditionally(validCoordinateCondition(coordinate),
                           { WrongCoordinate("Coordinate not allowed: $coordinate") },
                           { true })

  fun hasObstacle(coordinate: Coordinate): Either<Error, Boolean> =
      either.eager {
        isValidCoordinate(coordinate).bind()
        && coordinates[coordinate.x][coordinate.y].isObstacle()
      }
}

package com.posadeus.rover.domain

sealed class Obstacle {

  fun isObstacle(): Boolean =
      when (this) {
        is Rock -> true
        is Empty -> false
      }
}

object Rock : Obstacle()
object Empty : Obstacle()

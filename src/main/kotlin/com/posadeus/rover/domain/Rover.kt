package com.posadeus.rover.domain

import com.posadeus.rover.domain.Orientation.*
import com.posadeus.rover.domain.exception.CommandNotFoundException
import com.posadeus.rover.domain.exception.WrongCoordinateException

data class Rover(private val mars: Mars,
                 private val coordinate: Coordinate,
                 val orientation: Orientation) {

  fun position(): Coordinate =
      if (mars.isValidCoordinate(coordinate))
        coordinate
      else
        throw WrongCoordinateException()

  fun execute(movements: Array<Char>): Rover {

    tailrec fun go(movements: Iterator<Char>, rover: Rover): Rover {

      return if (movements.hasNext()) {

        when (val movement = movements.next()) {
          'f', 'b' -> go(movements, rover.move(movement))
          'r', 'l' -> go(movements, rover.turn(movement))
          else -> throw CommandNotFoundException()
        }
      }
      else
        rover
    }

    return go(movements.iterator(), this)
  }

  fun move(movement: Char): Rover =
      Rover(mars,
            when (movement) {
              'f' -> forward()
              'b' -> backward()
              else -> throw CommandNotFoundException()
            },
            orientation)

  fun turn(movement: Char): Rover =
      Rover(mars,
            coordinate,
            when (movement) {
              'r' -> turnRight()
              'l' -> turnLeft()
              else -> throw CommandNotFoundException()
            })

  private fun forward(): Coordinate =
      when (orientation) {
        N -> Coordinate(coordinate.x, coordinate.y + 1)
        S -> Coordinate(coordinate.x, coordinate.y - 1)
        E -> Coordinate(coordinate.x + 1, coordinate.y)
        W -> Coordinate(coordinate.x - 1, coordinate.y)
      }

  private fun backward(): Coordinate =
      when (orientation) {
        N -> Coordinate(coordinate.x, coordinate.y - 1)
        S -> Coordinate(coordinate.x, coordinate.y + 1)
        E -> Coordinate(coordinate.x - 1, coordinate.y)
        W -> Coordinate(coordinate.x + 1, coordinate.y)
      }

  private fun turnRight(): Orientation =
      when (orientation) {
        N -> E
        S -> W
        E -> S
        W -> N
      }

  private fun turnLeft(): Orientation =
      when (orientation) {
        N -> W
        S -> E
        E -> N
        W -> S
      }
}

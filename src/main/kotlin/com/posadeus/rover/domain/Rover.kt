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

  fun execute(commands: Array<Char>): Rover {

    tailrec fun go(commands: Iterator<Char>, rover: Rover): Rover {

      return if (commands.hasNext()) {

        when (val command = commands.next()) {
          'f', 'b' -> go(commands, rover.move(command))
          'r', 'l' -> go(commands, rover.turn(command))
          else -> throw CommandNotFoundException()
        }
      }
      else
        rover
    }

    return go(commands.iterator(), this)
  }

  fun move(movement: Char): Rover =
      Rover(mars,
            when (movement) {
              'f' -> forward()
              'b' -> backward()
              else -> throw CommandNotFoundException()
            },
            orientation)

  fun turn(turn: Char): Rover =
      Rover(mars,
            coordinate,
            when (turn) {
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

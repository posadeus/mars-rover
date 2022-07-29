package com.posadeus.rover.domain

import arrow.core.Either
import com.posadeus.rover.domain.Orientation.*
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class CommandCenterTest {

  private val movement = mockk<Movement>()
  private val turn = mockk<Turn>()

  //  region initial position
  @Test
  internal fun `rover initial position`() {

    val rover = Rover(Coordinate(2, 2), N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    assertTrue { commandCenter.position() == Either.Right(Coordinate(2, 2)) }
    assertTrue { commandCenter.orientation() == N }
  }

  @Test
  internal fun `rover wrong initial position`() {

    val coordinate = Coordinate(6, 0)
    val rover = Rover(coordinate, N)
    val commandCenter = CommandCenter(mars, rover, movement, turn)

    assertTrue { commandCenter.position() == Either.Left(WrongCoordinate("Coordinate not allowed: $coordinate")) }
  }
// endregion

  // region execute moves
  @Test
  internal fun `execute forward direction command`() {

    val startCoordinate = Coordinate(2, 2)
    val rover = Rover(startCoordinate, N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    every { movement.calculate(mars, startCoordinate, 'f', N) } returns Either.Right(Coordinate(2, 3))

    assertTrue { commandCenter.execute(arrayOf('f')) == Either.Right(Rover(Coordinate(2, 3), N)) }
  }

  @Test
  internal fun `execute backward direction command`() {

    val startCoordinate = Coordinate(2, 2)
    val rover = Rover(startCoordinate, N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    every { movement.calculate(mars, startCoordinate, 'b', N) } returns Either.Right(Coordinate(2, 1))

    assertTrue { commandCenter.execute(arrayOf('b')) == Either.Right(Rover(Coordinate(2, 1), N)) }
  }

  @Test
  internal fun `execute moves over the edges`() {

    val startCoordinate = Coordinate(4, 4)
    val rover = Rover(startCoordinate, N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    every { movement.calculate(mars, startCoordinate, 'f', N) } returns Either.Right(Coordinate(4, 0))

    assertTrue { commandCenter.execute(arrayOf('f')) == Either.Right(Rover(Coordinate(4, 0), N)) }
  }
// endregion

  // region execute turns
  @Test
  internal fun `execute all turns right commands`() {

    val startCoordinate = Coordinate(2, 2)
    val rover = Rover(startCoordinate, N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    every { turn.turn('r', N) } returns Either.Right(E)

    assertTrue { commandCenter.execute(arrayOf('r')) == Either.Right(Rover(startCoordinate, E)) }
  }

  @Test
  internal fun `execute all turns left commands`() {

    val startCoordinate = Coordinate(2, 2)
    val rover = Rover(startCoordinate, N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    every { turn.turn('l', N) } returns Either.Right(W)

    assertTrue { commandCenter.execute(arrayOf('l')) == Either.Right(Rover(startCoordinate, W)) }
  }
  // endregion

  // region execute sequence
  @Test
  internal fun `execute forward and backward commands`() {

    val rover = Rover(Coordinate(2, 2), N)
    val movement = Movement()
    val turn = Turn()
    val commandCenter = CommandCenter(mars, rover, movement, turn)

    assertTrue { commandCenter.execute(arrayOf('f', 'f', 'b')) == Either.Right(Rover(Coordinate(2, 3), N)) }
  }

  @Test
  internal fun `execute forward and turns commands`() {

    val rover = Rover(Coordinate(2, 2), N)
    val moves = Movement()
    val turns = Turn()
    val commandCenter = CommandCenter(mars, rover, moves, turns)

    assertTrue {
      commandCenter.execute(arrayOf('f', 'l', 'f', 'r', 'f', 'r')) == Either.Right(Rover(Coordinate(1, 4), E))
    }
  }

  @Test
  internal fun `execute forward and turns commands with obstacle on the way`() {

    val rover = Rover(Coordinate(2, 2), N)
    val moves = Movement()
    val turns = Turn()
    val commandCenter = CommandCenter(mars, rover, moves, turns)

    assertTrue {
      commandCenter.execute(arrayOf('f', 'r', 'f', 'l', 'f', 'r')) == Either.Right(Rover(Coordinate(3, 3), N))
    }
  }
  // endregion

  // region execute exceptions
  @Test
  internal fun `execute command not found`() {

    val rover = Rover(Coordinate(2, 2), N)
    val movement = Movement()
    val turn = Turn()
    val commandCenter = CommandCenter(mars, rover, movement, turn)

    val execute = commandCenter.execute(arrayOf('k'))

    assertTrue { execute.isLeft() }
    assertTrue { execute.fold({ it }, { it }) == CommandNotFound("Command not found 'k'") }
  }
  // endregion

  // region detect obstacle
  @Test
  internal fun `detect obstacle and abort movements`() {

    val startCoordinate = Coordinate(0, 1)
    val rover = Rover(startCoordinate, N)

    val missionAborted = MissionAborted("Obstacle found: ${Coordinate(1, 1)}")

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    every { movement.calculate(mars, startCoordinate, 'f', N) } returns Either.Right(Coordinate(0, 2))
    every { movement.calculate(mars, Coordinate(0, 2), 'f', N) } returns Either.Left(missionAborted)

    assertTrue { commandCenter.execute(arrayOf('f', 'f')) == Either.Right(Rover(Coordinate(0, 2), N)) }
  }
  // endregion

  companion object {

    private val mars = Mars(arrayOf(arrayOf(Empty, Empty, Empty, Rock, Empty),
                                    arrayOf(Empty, Rock, Empty, Empty, Empty),
                                    arrayOf(Empty, Empty, Empty, Empty, Empty),
                                    arrayOf(Empty, Empty, Empty, Empty, Rock),
                                    arrayOf(Empty, Rock, Rock, Empty, Empty)))
  }
}
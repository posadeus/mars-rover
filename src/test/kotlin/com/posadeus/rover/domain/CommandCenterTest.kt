package com.posadeus.rover.domain

import com.posadeus.rover.domain.Orientation.*
import com.posadeus.rover.domain.exception.CommandNotFoundException
import com.posadeus.rover.domain.exception.WrongCoordinateException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

class CommandCenterTest {

  private val movement = mockk<Movement>()
  private val turn = mockk<Turn>()

  //  region initial position
  @Test
  internal fun `rover initial position`() {

    val rover = Rover(Coordinate(0, 0), N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    assertTrue { commandCenter.position() == Coordinate(0, 0) }
    assertTrue { commandCenter.orientation() == N }
  }

  @Test
  internal fun `rover wrong initial position`() {

    val rover = Rover(Coordinate(-3, 0), N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    assertThrows<WrongCoordinateException> { commandCenter.position() }
  }
// endregion

  // region execute moves
  @Test
  internal fun `execute all forward direction commands`() {

    val roverN = Rover(Coordinate(0, 0), N)
    val roverE = Rover(Coordinate(0, 0), E)
    val roverS = Rover(Coordinate(0, 0), S)
    val roverW = Rover(Coordinate(0, 0), W)

    val startCoordinate = Coordinate(0, 0)
    val commandCenterN = CommandCenter(mars, roverN, movement, turn)
    val commandCenterE = CommandCenter(mars, roverE, movement, turn)
    val commandCenterS = CommandCenter(mars, roverS, movement, turn)
    val commandCenterW = CommandCenter(mars, roverW, movement, turn)

    every { movement.move(startCoordinate, 'f', N) } returns Coordinate(0, 1)
    every { movement.move(startCoordinate, 'f', E) } returns Coordinate(1, 0)
    every { movement.move(startCoordinate, 'f', S) } returns Coordinate(0, -1)
    every { movement.move(startCoordinate, 'f', W) } returns Coordinate(-1, 0)

    assertTrue { commandCenterN.execute(arrayOf('f')) == Rover(Coordinate(0, 1), N) }
    assertTrue { commandCenterE.execute(arrayOf('f')) == Rover(Coordinate(1, 0), E) }
    assertTrue { commandCenterS.execute(arrayOf('f')) == Rover(Coordinate(0, -1), S) }
    assertTrue { commandCenterW.execute(arrayOf('f')) == Rover(Coordinate(-1, 0), W) }
  }

  @Test
  internal fun `execute all backward direction commands`() {

    val roverN = Rover(Coordinate(0, 0), N)
    val roverE = Rover(Coordinate(0, 0), E)
    val roverS = Rover(Coordinate(0, 0), S)
    val roverW = Rover(Coordinate(0, 0), W)

    val startCoordinate = Coordinate(0, 0)
    val commandCenterN = CommandCenter(mars, roverN, movement, turn)
    val commandCenterE = CommandCenter(mars, roverE, movement, turn)
    val commandCenterS = CommandCenter(mars, roverS, movement, turn)
    val commandCenterW = CommandCenter(mars, roverW, movement, turn)

    every { movement.move(startCoordinate, 'b', N) } returns Coordinate(0, -1)
    every { movement.move(startCoordinate, 'b', E) } returns Coordinate(-1, 0)
    every { movement.move(startCoordinate, 'b', S) } returns Coordinate(0, 1)
    every { movement.move(startCoordinate, 'b', W) } returns Coordinate(1, 0)

    assertTrue { commandCenterN.execute(arrayOf('b')) == Rover(Coordinate(0, -1), N) }
    assertTrue { commandCenterE.execute(arrayOf('b')) == Rover(Coordinate(-1, 0), E) }
    assertTrue { commandCenterS.execute(arrayOf('b')) == Rover(Coordinate(0, 1), S) }
    assertTrue { commandCenterW.execute(arrayOf('b')) == Rover(Coordinate(1, 0), W) }
  }
  // endregion

  // region execute turns
  @Test
  internal fun `execute all turns right commands`() {

    val roverN = Rover(Coordinate(0, 0), N)
    val roverE = Rover(Coordinate(0, 0), E)
    val roverS = Rover(Coordinate(0, 0), S)
    val roverW = Rover(Coordinate(0, 0), W)

    val startCoordinate = Coordinate(0, 0)
    val commandCenterN = CommandCenter(mars, roverN, movement, turn)
    val commandCenterE = CommandCenter(mars, roverE, movement, turn)
    val commandCenterS = CommandCenter(mars, roverS, movement, turn)
    val commandCenterW = CommandCenter(mars, roverW, movement, turn)

    every { turn.turn('r', N) } returns E
    every { turn.turn('r', E) } returns S
    every { turn.turn('r', S) } returns W
    every { turn.turn('r', W) } returns N

    assertTrue { commandCenterN.execute(arrayOf('r')) == Rover(startCoordinate, E) }
    assertTrue { commandCenterE.execute(arrayOf('r')) == Rover(startCoordinate, S) }
    assertTrue { commandCenterS.execute(arrayOf('r')) == Rover(startCoordinate, W) }
    assertTrue { commandCenterW.execute(arrayOf('r')) == Rover(startCoordinate, N) }
  }

  @Test
  internal fun `execute all turns left commands`() {

    val roverN = Rover(Coordinate(0, 0), N)
    val roverE = Rover(Coordinate(0, 0), E)
    val roverS = Rover(Coordinate(0, 0), S)
    val roverW = Rover(Coordinate(0, 0), W)

    val startCoordinate = Coordinate(0, 0)
    val commandCenterN = CommandCenter(mars, roverN, movement, turn)
    val commandCenterE = CommandCenter(mars, roverE, movement, turn)
    val commandCenterS = CommandCenter(mars, roverS, movement, turn)
    val commandCenterW = CommandCenter(mars, roverW, movement, turn)

    every { turn.turn('l', N) } returns W
    every { turn.turn('l', E) } returns N
    every { turn.turn('l', S) } returns E
    every { turn.turn('l', W) } returns S

    assertTrue { commandCenterN.execute(arrayOf('l')) == Rover(startCoordinate, W) }
    assertTrue { commandCenterE.execute(arrayOf('l')) == Rover(startCoordinate, N) }
    assertTrue { commandCenterS.execute(arrayOf('l')) == Rover(startCoordinate, E) }
    assertTrue { commandCenterW.execute(arrayOf('l')) == Rover(startCoordinate, S) }
  }
  // endregion

  // region execute sequence
  @Test
  internal fun `execute forward and backward commands`() {

    val rover = Rover(Coordinate(0, 0), N)
    val movement = Movement()
    val turn = Turn()
    val commandCenter = CommandCenter(mars, rover, movement, turn)

    assertTrue { commandCenter.execute(arrayOf('f', 'f', 'b')) == Rover(Coordinate(0, 1), N) }
  }

  @Test
  internal fun `execute forward and turns commands`() {

    val rover = Rover(Coordinate(0, 0), N)
    val moves = Movement()
    val turns = Turn()
    val commandCenter = CommandCenter(mars, rover, moves, turns)

    assertTrue { commandCenter.execute(arrayOf('f', 'r', 'f', 'l', 'f', 'r')) == Rover(Coordinate(1, 2), E) }
  }
  // endregion

  // region execute exceptions
  @Test
  internal fun `execute command not found`() {

    val rover = Rover(Coordinate(0, 0), N)
    val movement = Movement()
    val turn = Turn()
    val commandCenter = CommandCenter(mars, rover, movement, turn)

    assertThrows<CommandNotFoundException> { commandCenter.execute(arrayOf('k')) }
  }
  // endregion
  companion object {

    private val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                            arrayOf(-2, -1, 0, 1, 2),
                            emptyArray(),
                            arrayOf(arrayOf(null, null, null, Obstacle(Coordinate(0, 3)), null),
                                    arrayOf(null, Obstacle(Coordinate(1, 1)), null, null, null),
                                    arrayOf(null, null, null, null, null),
                                    arrayOf(null, null, null, null, Obstacle(Coordinate(3, 4))),
                                    arrayOf(null, Obstacle(Coordinate(4, 1)), Obstacle(Coordinate(4, 2)), null, null)))
  }
}
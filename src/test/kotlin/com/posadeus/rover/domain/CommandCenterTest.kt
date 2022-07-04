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

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    val commandCenter = CommandCenter(mars, Coordinate(0, 0), N, movement, turn)

    assertTrue { commandCenter.position() == Coordinate(0, 0) }
    assertTrue { commandCenter.orientation == N }
  }

  @Test
  internal fun `rover wrong initial position`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    val commandCenter = CommandCenter(mars, Coordinate(-3, 0), N, movement, turn)

    assertThrows<WrongCoordinateException> { commandCenter.position() }
  }
// endregion

  // region execute moves
  @Test
  internal fun `execute all forward direction commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    val startCoordinate = Coordinate(0, 0)
    val commandCenterN = CommandCenter(mars, startCoordinate, N, movement, turn)
    val commandCenterE = CommandCenter(mars, startCoordinate, E, movement, turn)
    val commandCenterS = CommandCenter(mars, startCoordinate, S, movement, turn)
    val commandCenterW = CommandCenter(mars, startCoordinate, W, movement, turn)

    every { movement.move(startCoordinate, 'f', N) } returns Coordinate(0, 1)
    every { movement.move(startCoordinate, 'f', E) } returns Coordinate(1, 0)
    every { movement.move(startCoordinate, 'f', S) } returns Coordinate(0, -1)
    every { movement.move(startCoordinate, 'f', W) } returns Coordinate(-1, 0)

    assertTrue { commandCenterN.execute(arrayOf('f')) == CommandCenter(mars, Coordinate(0, 1), N, movement, turn) }
    assertTrue { commandCenterE.execute(arrayOf('f')) == CommandCenter(mars, Coordinate(1, 0), E, movement, turn) }
    assertTrue { commandCenterS.execute(arrayOf('f')) == CommandCenter(mars, Coordinate(0, -1), S, movement, turn) }
    assertTrue { commandCenterW.execute(arrayOf('f')) == CommandCenter(mars, Coordinate(-1, 0), W, movement, turn) }
  }

  @Test
  internal fun `execute all backward direction commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    val startCoordinate = Coordinate(0, 0)
    val commandCenterN = CommandCenter(mars, startCoordinate, N, movement, turn)
    val commandCenterE = CommandCenter(mars, startCoordinate, E, movement, turn)
    val commandCenterS = CommandCenter(mars, startCoordinate, S, movement, turn)
    val commandCenterW = CommandCenter(mars, startCoordinate, W, movement, turn)

    every { movement.move(startCoordinate, 'b', N) } returns Coordinate(0, -1)
    every { movement.move(startCoordinate, 'b', E) } returns Coordinate(-1, 0)
    every { movement.move(startCoordinate, 'b', S) } returns Coordinate(0, 1)
    every { movement.move(startCoordinate, 'b', W) } returns Coordinate(1, 0)

    assertTrue { commandCenterN.execute(arrayOf('b')) == CommandCenter(mars, Coordinate(0, -1), N, movement, turn) }
    assertTrue { commandCenterE.execute(arrayOf('b')) == CommandCenter(mars, Coordinate(-1, 0), E, movement, turn) }
    assertTrue { commandCenterS.execute(arrayOf('b')) == CommandCenter(mars, Coordinate(0, 1), S, movement, turn) }
    assertTrue { commandCenterW.execute(arrayOf('b')) == CommandCenter(mars, Coordinate(1, 0), W, movement, turn) }
  }
  // endregion

  // region execute turns
  @Test
  internal fun `execute all turns right commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    val startCoordinate = Coordinate(0, 0)
    val commandCenterN = CommandCenter(mars, startCoordinate, N, movement, turn)
    val commandCenterE = CommandCenter(mars, startCoordinate, E, movement, turn)
    val commandCenterS = CommandCenter(mars, startCoordinate, S, movement, turn)
    val commandCenterW = CommandCenter(mars, startCoordinate, W, movement, turn)

    every { turn.turn('r', N) } returns E
    every { turn.turn('r', E) } returns S
    every { turn.turn('r', S) } returns W
    every { turn.turn('r', W) } returns N

    assertTrue { commandCenterN.execute(arrayOf('r')) == CommandCenter(mars, startCoordinate, E, movement, turn) }
    assertTrue { commandCenterE.execute(arrayOf('r')) == CommandCenter(mars, startCoordinate, S, movement, turn) }
    assertTrue { commandCenterS.execute(arrayOf('r')) == CommandCenter(mars, startCoordinate, W, movement, turn) }
    assertTrue { commandCenterW.execute(arrayOf('r')) == CommandCenter(mars, startCoordinate, N, movement, turn) }
  }

  @Test
  internal fun `execute all turns left commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    val startCoordinate = Coordinate(0, 0)
    val commandCenterN = CommandCenter(mars, startCoordinate, N, movement, turn)
    val commandCenterE = CommandCenter(mars, startCoordinate, E, movement, turn)
    val commandCenterS = CommandCenter(mars, startCoordinate, S, movement, turn)
    val commandCenterW = CommandCenter(mars, startCoordinate, W, movement, turn)

    every { turn.turn('l', N) } returns W
    every { turn.turn('l', E) } returns N
    every { turn.turn('l', S) } returns E
    every { turn.turn('l', W) } returns S

    assertTrue { commandCenterN.execute(arrayOf('l')) == CommandCenter(mars, startCoordinate, W, movement, turn) }
    assertTrue { commandCenterE.execute(arrayOf('l')) == CommandCenter(mars, startCoordinate, N, movement, turn) }
    assertTrue { commandCenterS.execute(arrayOf('l')) == CommandCenter(mars, startCoordinate, E, movement, turn) }
    assertTrue { commandCenterW.execute(arrayOf('l')) == CommandCenter(mars, startCoordinate, S, movement, turn) }
  }
  // endregion

  // region execute sequence
  @Test
  internal fun `execute forward and backward commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val movement = Movement()
    val turn = Turn()
    val commandCenter = CommandCenter(mars, Coordinate(0, 0), N, movement, turn)

    assertTrue {
      commandCenter.execute(arrayOf('f', 'f', 'b')) == CommandCenter(mars,
                                                                     Coordinate(0, 1),
                                                                     N,
                                                                     movement,
                                                                     turn)
    }
  }

  @Test
  internal fun `execute forward and turns commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val moves = Movement()
    val turns = Turn()
    val commandCenter = CommandCenter(mars, Coordinate(0, 0), N, moves, turns)

    assertTrue {
      commandCenter.execute(arrayOf('f', 'r', 'f', 'l', 'f', 'r')) == CommandCenter(mars,
                                                                                    Coordinate(1, 2),
                                                                                    E,
                                                                                    movement,
                                                                                    turn)
    }
  }
  // endregion

  // region execute exceptions
  @Test
  internal fun `execute command not found`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val movement = Movement()
    val turn = Turn()
    val commandCenter = CommandCenter(mars, Coordinate(0, 0), N, movement, turn)

    assertThrows<CommandNotFoundException> { commandCenter.execute(arrayOf('k')) }
  }
  // endregion
}
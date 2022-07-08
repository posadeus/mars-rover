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

    val rover = Rover(Coordinate(2, 2), N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    assertTrue { commandCenter.position() == Coordinate(2, 2) }
    assertTrue { commandCenter.orientation() == N }
  }

  @Test
  internal fun `rover wrong initial position`() {

    val rover = Rover(Coordinate(6, 0), N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    assertThrows<WrongCoordinateException> { commandCenter.position() }
  }
// endregion

  // region execute moves
  @Test
  internal fun `execute forward direction command`() {

    val startCoordinate = Coordinate(2, 2)
    val rover = Rover(startCoordinate, N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    every { movement.move(mars, startCoordinate, 'f', N) } returns Coordinate(2, 3)

    assertTrue { commandCenter.execute(arrayOf('f')) == Rover(Coordinate(2, 3), N) }
  }

  @Test
  internal fun `execute backward direction command`() {

    val startCoordinate = Coordinate(2, 2)
    val rover = Rover(startCoordinate, N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    every { movement.move(mars, startCoordinate, 'b', N) } returns Coordinate(2, 1)

    assertTrue { commandCenter.execute(arrayOf('b')) == Rover(Coordinate(2, 1), N) }
  }

  @Test
  internal fun `execute moves over the edges`() {

    val startCoordinate = Coordinate(4, 4)
    val rover = Rover(startCoordinate, N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    every { movement.move(mars, startCoordinate, 'f', N) } returns Coordinate(4, 0)

    assertTrue { commandCenter.execute(arrayOf('f')) == Rover(Coordinate(4, 0), N) }
  }
// endregion

  // region execute turns
  @Test
  internal fun `execute all turns right commands`() {

    val startCoordinate = Coordinate(2, 2)
    val rover = Rover(startCoordinate, N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    every { turn.turn('r', N) } returns E

    assertTrue { commandCenter.execute(arrayOf('r')) == Rover(startCoordinate, E) }
  }

  @Test
  internal fun `execute all turns left commands`() {

    val startCoordinate = Coordinate(2, 2)
    val rover = Rover(startCoordinate, N)

    val commandCenter = CommandCenter(mars, rover, movement, turn)

    every { turn.turn('l', N) } returns W

    assertTrue { commandCenter.execute(arrayOf('l')) == Rover(startCoordinate, W) }
  }
  // endregion

  // region execute sequence
  @Test
  internal fun `execute forward and backward commands`() {

    val rover = Rover(Coordinate(2, 2), N)
    val movement = Movement()
    val turn = Turn()
    val commandCenter = CommandCenter(mars, rover, movement, turn)

    assertTrue { commandCenter.execute(arrayOf('f', 'f', 'b')) == Rover(Coordinate(2, 3), N) }
  }

  @Test
  internal fun `execute forward and turns commands`() {

    val rover = Rover(Coordinate(2, 2), N)
    val moves = Movement()
    val turns = Turn()
    val commandCenter = CommandCenter(mars, rover, moves, turns)

    assertTrue { commandCenter.execute(arrayOf('f', 'r', 'f', 'l', 'f', 'r')) == Rover(Coordinate(3, 4), E) }
  }
  // endregion

  // region execute exceptions
  @Test
  internal fun `execute command not found`() {

    val rover = Rover(Coordinate(2, 2), N)
    val movement = Movement()
    val turn = Turn()
    val commandCenter = CommandCenter(mars, rover, movement, turn)

    assertThrows<CommandNotFoundException> { commandCenter.execute(arrayOf('k')) }
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
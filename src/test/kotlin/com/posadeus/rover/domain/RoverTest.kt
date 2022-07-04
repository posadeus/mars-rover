package com.posadeus.rover.domain

import com.posadeus.rover.domain.Orientation.*
import com.posadeus.rover.domain.exception.CommandNotFoundException
import com.posadeus.rover.domain.exception.WrongCoordinateException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

class RoverTest {

  //  region initial position
  @Test
  internal fun `rover initial position`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    val rover = Rover(mars, Coordinate(0, 0), N)

    assertTrue { rover.position() == Coordinate(0, 0) }
    assertTrue { rover.orientation == N }
  }

  @Test
  internal fun `rover wrong initial position`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    val rover = Rover(mars, Coordinate(-3, 0), N)

    assertThrows<WrongCoordinateException> { rover.position() }
  }
// endregion

  // region moves
  @Test
  internal fun `move command not found`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertThrows<CommandNotFoundException> { rover.move('k') }
  }
  // endregion

  // region turns
  @Test
  internal fun `turn command not found`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertThrows<CommandNotFoundException> { rover.turn('k') }
  }
  // endregion

  // region execute moves
  @Test
  internal fun `execute all forward direction commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    assertTrue { Rover(mars, Coordinate(0, 0), N).execute(arrayOf('f')) == Rover(mars, Coordinate(0, 1), N) }
    assertTrue { Rover(mars, Coordinate(0, 0), E).execute(arrayOf('f')) == Rover(mars, Coordinate(1, 0), E) }
    assertTrue { Rover(mars, Coordinate(0, 0), S).execute(arrayOf('f')) == Rover(mars, Coordinate(0, -1), S) }
    assertTrue { Rover(mars, Coordinate(0, 0), W).execute(arrayOf('f')) == Rover(mars, Coordinate(-1, 0), W) }
  }

  @Test
  internal fun `execute all backward direction commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    assertTrue { Rover(mars, Coordinate(0, 0), N).execute(arrayOf('b')) == Rover(mars, Coordinate(0, -1), N) }
    assertTrue { Rover(mars, Coordinate(0, 0), E).execute(arrayOf('b')) == Rover(mars, Coordinate(-1, 0), E) }
    assertTrue { Rover(mars, Coordinate(0, 0), S).execute(arrayOf('b')) == Rover(mars, Coordinate(0, 1), S) }
    assertTrue { Rover(mars, Coordinate(0, 0), W).execute(arrayOf('b')) == Rover(mars, Coordinate(1, 0), W) }
  }
  // endregion

  // region execute turns
  @Test
  internal fun `execute all turns right commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    assertTrue { Rover(mars, Coordinate(0, 0), N).execute(arrayOf('r')) == Rover(mars, Coordinate(0, 0), E) }
    assertTrue { Rover(mars, Coordinate(0, 0), E).execute(arrayOf('r')) == Rover(mars, Coordinate(0, 0), S) }
    assertTrue { Rover(mars, Coordinate(0, 0), S).execute(arrayOf('r')) == Rover(mars, Coordinate(0, 0), W) }
    assertTrue { Rover(mars, Coordinate(0, 0), W).execute(arrayOf('r')) == Rover(mars, Coordinate(0, 0), N) }
  }

  @Test
  internal fun `execute all turns left commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))

    assertTrue { Rover(mars, Coordinate(0, 0), N).execute(arrayOf('l')) == Rover(mars, Coordinate(0, 0), W) }
    assertTrue { Rover(mars, Coordinate(0, 0), E).execute(arrayOf('l')) == Rover(mars, Coordinate(0, 0), N) }
    assertTrue { Rover(mars, Coordinate(0, 0), S).execute(arrayOf('l')) == Rover(mars, Coordinate(0, 0), E) }
    assertTrue { Rover(mars, Coordinate(0, 0), W).execute(arrayOf('l')) == Rover(mars, Coordinate(0, 0), S) }
  }
  // endregion

  // region execute sequence
  @Test
  internal fun `execute forward and backward commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertTrue { rover.execute(arrayOf('f', 'f', 'b')) == Rover(mars, Coordinate(0, 1), N) }
  }

  @Test
  internal fun `execute forward and turns commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertTrue { rover.execute(arrayOf('f', 'r', 'f', 'l', 'f', 'r')) == Rover(mars, Coordinate(1, 2), E) }
  }
  // endregion

  // region execute exceptions
  @Test
  internal fun `execute command not found`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertThrows<CommandNotFoundException> { rover.execute(arrayOf('k')) }
  }
  // endregion
}
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
  internal fun `move forward on Y with N orientation`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertTrue { rover.move('f') == Rover(mars, Coordinate(0, 1), N) }
  }

  @Test
  internal fun `move forward on Y with S orientation`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), S)

    assertTrue { rover.move('f') == Rover(mars, Coordinate(0, -1), S) }
  }

  @Test
  internal fun `move forward on X with E orientation`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), E)

    assertTrue { rover.move('f') == Rover(mars, Coordinate(1, 0), E) }
  }

  @Test
  internal fun `move forward on X with W orientation`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), W)

    assertTrue { rover.move('f') == Rover(mars, Coordinate(-1, 0), W) }
  }

  @Test
  internal fun `move backward on Y with orientation N`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertTrue { rover.move('b') == Rover(mars, Coordinate(0, -1), N) }
  }

  @Test
  internal fun `move backward on Y with orientation S`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), S)

    assertTrue { rover.move('b') == Rover(mars, Coordinate(0, 1), S) }
  }

  @Test
  internal fun `move backward on X with orientation E`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), E)

    assertTrue { rover.move('b') == Rover(mars, Coordinate(-1, 0), E) }
  }

  @Test
  internal fun `move backward on X with orientation W`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), W)

    assertTrue { rover.move('b') == Rover(mars, Coordinate(1, 0), W) }
  }

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

  @Test
  internal fun `execute all turns right commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertTrue { rover.execute(arrayOf('r')) == Rover(mars, Coordinate(0, 0), E) }
    assertTrue { rover.execute(arrayOf('r', 'r')) == Rover(mars, Coordinate(0, 0), S) }
    assertTrue { rover.execute(arrayOf('r', 'r', 'r')) == Rover(mars, Coordinate(0, 0), W) }
    assertTrue { rover.execute(arrayOf('r', 'r', 'r', 'r')) == Rover(mars, Coordinate(0, 0), N) }
  }

  @Test
  internal fun `execute all turns left commands`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertTrue { rover.execute(arrayOf('l')) == Rover(mars, Coordinate(0, 0), W) }
    assertTrue { rover.execute(arrayOf('l', 'l')) == Rover(mars, Coordinate(0, 0), S) }
    assertTrue { rover.execute(arrayOf('l', 'l', 'l')) == Rover(mars, Coordinate(0, 0), E) }
    assertTrue { rover.execute(arrayOf('l', 'l', 'l', 'l')) == Rover(mars, Coordinate(0, 0), N) }
  }

  @Test
  internal fun `execute command not found`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertThrows<CommandNotFoundException> { rover.execute(arrayOf('k')) }
  }
}
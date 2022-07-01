package com.posadeus.rover.domain

import com.posadeus.rover.domain.Orientation.*
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

  //  region plan move forward
  @Test
  internal fun `plan move forward on Y with N orientation`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertTrue { rover.forward() == Coordinate(0, 1) }
  }

  @Test
  internal fun `plan move forward on Y with S orientation`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), S)

    assertTrue { rover.forward() == Coordinate(0, -1) }
  }

  @Test
  internal fun `plan move forward on X with E orientation`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), E)

    assertTrue { rover.forward() == Coordinate(1, 0) }
  }

  @Test
  internal fun `plan move forward on X with W orientation`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), W)

    assertTrue { rover.forward() == Coordinate(-1, 0) }
  }
//  endregion

  // region plan move backward
  @Test
  internal fun `plan move backward on Y with orientation N`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertTrue { rover.backward() == Coordinate(0, -1) }
  }

  @Test
  internal fun `plan move backward on Y with orientation S`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), S)

    assertTrue { rover.backward() == Coordinate(0, 1) }
  }

  @Test
  internal fun `plan move backward on X with orientation E`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), E)

    assertTrue { rover.backward() == Coordinate(-1, 0) }
  }

  @Test
  internal fun `plan move backward on X with orientation W`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), W)

    assertTrue { rover.backward() == Coordinate(1, 0) }
  }
//  endregion

  @Test
  internal fun `move forward`() {

    val mars = Mars(arrayOf(-2, -1, 0, 1, 2),
                    arrayOf(-2, -1, 0, 1, 2))
    val rover = Rover(mars, Coordinate(0, 0), N)

    assertTrue { rover.move('f') == Rover(mars, Coordinate(0, 1), N) }
  }
}
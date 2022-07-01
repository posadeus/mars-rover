package com.posadeus.rover

import com.posadeus.mars.Mars

class Rover(private val mars: Mars,
            private val x: Int,
            private val y: Int) {

  fun position(): Pair<Int, Int> =
      mars.coordinate(x, y)
}

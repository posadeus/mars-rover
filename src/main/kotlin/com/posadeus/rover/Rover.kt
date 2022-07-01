package com.posadeus.rover

import com.posadeus.mars.Mars

class Rover(private val mars: Mars,
            private val x: Int,
            private val y: Int,
            val orientation: String) {

  fun position(): Pair<Int, Int> =
      mars.coordinate(x, y)

  fun forward(): Pair<Int, Int> =
      if (orientation == "N") mars.coordinate(x, y + 1)
      else mars.coordinate(x + 1, y)
}

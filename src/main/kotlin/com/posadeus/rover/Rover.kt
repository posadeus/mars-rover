package com.posadeus.rover

class Rover(private val x: Int,
            private val y: Int) {

  fun position(): Pair<Int, Int> =
      Pair(x, y)
}

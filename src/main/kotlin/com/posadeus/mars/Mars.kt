package com.posadeus.mars

class Mars(private val x: Array<Int>,
           private val y: Array<Int>) {

  fun coordinate(x: Int, y: Int): Pair<Int, Int> =
      Pair(this.x[x], this.y[y])
}

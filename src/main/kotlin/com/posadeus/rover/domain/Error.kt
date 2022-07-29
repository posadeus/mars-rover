package com.posadeus.rover.domain

import java.util.logging.Logger

sealed class Error(message: String) {

  init {

    logger.warning(message)
  }

  companion object {

    private val logger = Logger.getLogger("Error")
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    return true
  }

  override fun hashCode(): Int {
    return javaClass.hashCode()
  }
}

class CommandNotFound(message: String) : Error(message)
class MissionAborted(message: String) : Error(message)
class WrongCoordinate(message: String) : Error(message)

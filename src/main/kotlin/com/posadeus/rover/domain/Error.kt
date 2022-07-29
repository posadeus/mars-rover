package com.posadeus.rover.domain

sealed class Error

data class CommandNotFound(val message: String) : Error()
data class MissionAborted(val message: String) : Error()
data class WrongCoordinate(val message: String) : Error()

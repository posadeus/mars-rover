package com.posadeus.rover.domain.exception

class MissionAbortedException(override val message: String) : RuntimeException(message)
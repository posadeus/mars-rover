package com.posadeus.rover.domain

sealed class Error

object CommandNotFound : Error()
object WrongCoordinate : Error()
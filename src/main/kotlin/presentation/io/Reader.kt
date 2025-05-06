package com.berlin.presentation.io

interface Reader {
    fun read(): String?
    fun readLatitude(): Double?
    fun readLongitude(): Double?
}
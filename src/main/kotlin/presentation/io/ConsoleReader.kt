package com.berlin.presentation.io

class ConsoleReader : Reader {
    override fun read(): String? {
        return readlnOrNull()
    }

    override fun readLatitude(): Double? {
        val latInput = readlnOrNull()?.trim() ?: return null
        if (latInput.equals("back", ignoreCase = true)) return null

        val latitude = latInput.toDoubleOrNull()
        if (latitude != null) {
            return latitude
        }
        return null
    }

    override fun readLongitude(): Double? {
        val lonInput = readlnOrNull()?.trim() ?: return null
        if (lonInput.equals("back", ignoreCase = true)) return null

        val longitude = lonInput.toDoubleOrNull()
        if (longitude != null) {
            return longitude
        }
        return null
    }
}

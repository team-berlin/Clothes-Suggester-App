package com.berlin.data.mapper

interface Mapper<From, To> {
    fun map(from: From): To
}
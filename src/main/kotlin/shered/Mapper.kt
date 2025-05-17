package com.berlin.shered

interface Mapper<From, To> {
    fun map(from: From): To
}
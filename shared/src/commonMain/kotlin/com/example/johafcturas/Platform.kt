package com.example.johafcturas

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
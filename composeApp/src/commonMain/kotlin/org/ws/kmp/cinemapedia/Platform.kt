package org.ws.kmp.cinemapedia

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
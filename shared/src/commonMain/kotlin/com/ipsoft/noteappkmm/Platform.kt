package com.ipsoft.noteappkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
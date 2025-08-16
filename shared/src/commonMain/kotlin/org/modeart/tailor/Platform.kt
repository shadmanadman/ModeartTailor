package org.modeart.tailor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
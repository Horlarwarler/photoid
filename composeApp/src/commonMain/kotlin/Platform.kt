interface Platform {
    val name: String
}

expect fun getPlatform(): String
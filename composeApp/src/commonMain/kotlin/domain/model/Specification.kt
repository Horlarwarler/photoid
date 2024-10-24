package domain.model

data class Specification(
    val id: String,
    val width: Int,
    val height: Int,
    val name: String,
    val specificationType: SpecificationType,
    val description: String?,
    val dpi: Int = 300
) {


    fun toInch(): Pair<Double, Double> {
        val heightInInch = (height / 25.4)
        val widthInInch = (width / 25.4)
        return Pair(widthInInch, heightInInch)
    }

    fun toPx(
        dpi: Int = 300
    ): Pair<Int, Int> {
        val inchPair = toInch()
        val widthInPx = (inchPair.first * dpi).toInt()
        val heightInPx = (inchPair.second * dpi).toInt()
        return Pair(widthInPx, heightInPx)
    }

}

enum class SpecificationType {
    VISA,
    PASSPORT,
    CV_PHOTO,
    CUSTOM

}

fun SpecificationType.tolower(): String {

    return name.lowercase()
}


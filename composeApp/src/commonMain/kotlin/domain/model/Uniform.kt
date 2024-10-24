package domain.model

data class Uniform(
    val uniformId: String,
    val uniformUrl: String,
    val uniformCategory: String
)

enum class UniformCategory {
    MEN,
    WOMEN,
    CHILD
}
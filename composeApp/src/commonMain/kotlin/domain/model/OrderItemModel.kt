package domain.model

data class OrderItemModel(
    val orderId: String,
    val orderName: String,
    val productionTime: String,
    val expirationDate: String,
    val status: OrderStatus
)

enum class OrderStatus {
    PAID,
    PENDING
}

package presentation.order

import domain.model.OrderItemModel

data class OrdersScreenState(
    val orders: List<OrderItemModel> = emptyList()
)

package com.itmo.microservices.demo.delivery.api.model
import java.util.*

class DeliveryModel (
    val orderId: UUID,
    val deliveryId: UUID,
    val transactionId: UUID,
    val deliveryInfoRecordId: UUID,
    val slotInSec: Long,
)

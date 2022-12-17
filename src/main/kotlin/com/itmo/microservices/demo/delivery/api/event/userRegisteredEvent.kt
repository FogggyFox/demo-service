package com.itmo.microservices.demo.delivery.api.event
import ru.quipy.core.annotations.DomainEvent;
import ru.quipy.domain.Event;
import java.util.*
const val DELIVERY_SLOT_BOOKED= "DELIVERY_SLOT_BOOKED"

@DomainEvent(name = DELIVERY_SLOT_BOOKED)
class DeliverySlotBookedEvent (
    val orderId: UUID,
    val slotInSec: Int,
    createdAt: Long = System.currentTimeMillis()

) : Event<DeliveryAggregate>(
    name = DELIVERY_SLOT_BOOKED,
    createdAt = createdAt
)
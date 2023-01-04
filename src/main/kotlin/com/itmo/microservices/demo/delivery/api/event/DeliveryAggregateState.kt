package com.itmo.microservices.demo.delivery.api.event

import com.itmo.microservices.demo.delivery.api.model.DeliverySubmissionOutcome
import org.springframework.web.bind.annotation.PathVariable
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*


class DeliveryAggregateState: AggregateState<UUID, DeliveryAggregate> {
    private lateinit var deliveryId: UUID
    private lateinit var orderId: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()

    private lateinit var deliveryInfoRecord: DeliveryInfoRecordEntity
    override fun getId() = deliveryId

    fun setTime(OrderId: UUID, SlotInSec: Long, deliveryId: UUID = UUID.randomUUID(), transactionId: UUID): DeliverySlotBookedEvent {
        return DeliverySlotBookedEvent(OrderId, SlotInSec, deliveryId, transactionId)
    }

    @StateTransitionFunc
    fun setTime(event: DeliverySlotBookedEvent) {
        deliveryId = event.deliveryId
        orderId = event.orderId
        deliveryInfoRecord.submittedTime = event.slotInSec
        deliveryInfoRecord.transactionId  = event.transactionId
        deliveryInfoRecord.submissionStartedTime = event.createdAt
    }
}

data class DeliveryInfoRecordEntity(
    var outcome: DeliverySubmissionOutcome,
    var preparedTime: Long,
    var attempts: Int = 1,
    var submittedTime: Long,
    var transactionId: UUID,
    var submissionStartedTime: Long

)


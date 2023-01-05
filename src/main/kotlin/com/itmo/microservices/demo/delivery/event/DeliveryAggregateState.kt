package com.itmo.microservices.demo.delivery.event

import com.itmo.microservices.demo.delivery.api.model.DeliveryModel
import com.itmo.microservices.demo.delivery.api.model.DeliverySubmissionOutcome
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*


class DeliveryAggregateState: AggregateState<UUID, DeliveryAggregate> {
    private lateinit var deliveryId: UUID
    private lateinit var orderId: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()
    var deliveryInfoRecords: MutableMap<UUID, DeliveryInfoRecordEntity> = mutableMapOf()

    override fun getId() = deliveryId

    fun setTime(d: DeliveryModel): DeliverySlotBookedEvent {
            return DeliverySlotBookedEvent(d.orderId, d.slotInSec, d.deliveryId, d.transactionId, d.deliveryInfoRecordId)
    }

    @StateTransitionFunc
    fun setTime(event: DeliverySlotBookedEvent) {
        deliveryId = event.deliveryId
        orderId = event.orderId
        deliveryInfoRecords[event.deliveryInfoRecordId]!!.submittedTime = event.slotInSec
        deliveryInfoRecords[event.deliveryInfoRecordId]!!.transactionId  = event.transactionId
        deliveryInfoRecords[event.deliveryInfoRecordId]!!.submissionStartedTime = event.createdAt
    }
}

data class DeliveryInfoRecordEntity(
    var id: UUID,
    var outcome: DeliverySubmissionOutcome,
    var preparedTime: Long,
    var attempts: Int = 1,
    var submittedTime: Long,
    var transactionId: UUID,
    var submissionStartedTime: Long
)


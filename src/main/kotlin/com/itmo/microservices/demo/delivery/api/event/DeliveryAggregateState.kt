package com.itmo.microservices.demo.delivery.api.event

import com.itmo.microservices.demo.delivery.api.model.DeliverySubmissionOutcome
import org.springframework.web.bind.annotation.PathVariable
import ru.quipy.domain.AggregateState
import java.util.*


class DeliveryAggregateState: AggregateState<UUID, DeliveryAggregate> {
    private lateinit var deliveryId: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()

    private var deliveryInfoRecords = mutableMapOf<UUID, DeliveryInfoRecordEntity>()
    override fun getId() = deliveryId

    fun setTime(OrderId: UUID, SlotInSec: Int): DeliverySlotBookedEvent {
        return DeliverySlotBookedEvent(OrderId, SlotInSec)
    }
}

data class DeliveryInfoRecordEntity(
    val outcome: DeliverySubmissionOutcome? = null,
    val preparedTime: Long? = null,
    val attempts: Int = 1,
    val submittedTime: Long? = null,
    val transactionId: UUID? = null,
    val submissionStartedTime: Long? = null
)


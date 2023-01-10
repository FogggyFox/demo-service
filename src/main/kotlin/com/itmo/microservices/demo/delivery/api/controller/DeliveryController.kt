package com.itmo.microservices.demo.delivery.api.controller

import com.itmo.microservices.demo.delivery.event.DeliveryAggregate
import com.itmo.microservices.demo.delivery.event.DeliveryAggregateState
import com.itmo.microservices.demo.delivery.event.DeliveryInfoRecordEntity
import com.itmo.microservices.demo.delivery.event.DeliverySlotBookedEvent
import com.itmo.microservices.demo.delivery.api.model.DeliveryModel
import com.itmo.microservices.demo.delivery.deliverySlots
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("")
class DeliveryController(
    val deliveryEsService: EventSourcingService<UUID, DeliveryAggregate, DeliveryAggregateState>
) {

    @GetMapping("/delivery/slots?number={number}")
    @Operation(
        summary = "Get available slots of delivery",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Unauthorized", responseCode = "403", content = [Content()]),
            ApiResponse(description = "Delivery not found", responseCode = "404", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getDeliverySlots(@PathVariable number: Int): MutableList<Int> {
        return deliverySlots.freeSlots.asSequence().take(number).toMutableList()
    }

    @PostMapping("/orders/{d.orderId}/delivery?slot={d.slotInSec}")
    @Operation(
        summary = "Set time of delivery",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()]),
            ApiResponse(description = "Unauthorized", responseCode = "403", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun setTimeOfDelivery(@PathVariable d: DeliveryModel): DeliverySlotBookedEvent {
        return deliveryEsService.create { it.setTime(d) }
    }

    @GetMapping("/_internal//deliveryLog/{orderId}")
    @Operation(
        summary = "Get order delivery history",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Unauthorized", responseCode = "403", content = [Content()]),
            ApiResponse(description = "Delivery not found", responseCode = "404", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getDeliveryHistory(@PathVariable orderId: UUID): MutableMap<UUID, DeliveryInfoRecordEntity>?{
        return deliveryEsService.getState(orderId)?.deliveryInfoRecords
    }
}

package com.itmo.microservices.demo.delivery.api.controller

import com.itmo.microservices.demo.delivery.api.event.DeliveryAggregate
import com.itmo.microservices.demo.delivery.api.event.DeliveryAggregateState
import com.itmo.microservices.demo.delivery.api.event.DeliverySlotBookedEvent
import com.itmo.microservices.demo.delivery.api.model.BookingDTO
import com.itmo.microservices.demo.delivery.api.model.DeliveryInfoRecord
import com.itmo.microservices.demo.delivery.api.service.DeliveryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.hibernate.criterion.Order
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("")
class DeliveryController(val deliveryService: DeliveryService, val deliveryEsService: EventSourcingService<UUID, DeliveryAggregate, DeliveryAggregateState>) {

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
    fun getDeliverySlots(@PathVariable number: Int): List<Int> = deliveryService.getDeliverySlots(number)

    @PostMapping("/orders/{OrderId}/delivery?slot={SlotInSec}")
    @Operation(
        summary = "Set time of delivery",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()]),
            ApiResponse(description = "Unauthorized", responseCode = "403", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun setTimeOfDelivery(@PathVariable OrderId: UUID, @PathVariable SlotInSec: Int): DeliverySlotBookedEvent =
        deliveryEsService.create { it.setTime(OrderId, SlotInSec) }

    @GetMapping("/_internal/deliveryLog/{orderId}")
    @Operation(
        summary = "Get order delivery history",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Unauthorized", responseCode = "403", content = [Content()]),
            ApiResponse(description = "Delivery not found", responseCode = "404", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getDeliveryHistory(@PathVariable orderId: UUID): List<DeliveryInfoRecord> =
        deliveryService.getDeliveryHistory(orderId)
}
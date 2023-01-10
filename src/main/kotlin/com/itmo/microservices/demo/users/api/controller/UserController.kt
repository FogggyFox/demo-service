package com.itmo.microservices.demo.users.api.controller

import com.itmo.microservices.demo.users.api.model.RegistrationRequest
import com.itmo.microservices.demo.users.event.UserAggregate
import com.itmo.microservices.demo.users.event.UserAggregateState
import com.itmo.microservices.demo.users.event.UserRegisteredEvent
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/users")
class UserController (
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
    ) {

    @PostMapping
    @Operation(
        summary = "Register new user",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "Bad request", responseCode = "400", content = [Content()])
        ]
    )
    fun register(@RequestBody request: RegistrationRequest): UserRegisteredEvent {
        return userEsService.create { it.register(request)}
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get current user info",
        responses = [
            ApiResponse(description = "OK", responseCode = "200"),
            ApiResponse(description = "User not found", responseCode = "404", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getAccountData(@PathVariable id: UUID): UserAggregateState? {
        return userEsService.getState(id)
    }
}

package com.itmo.microservices.demo.delivery.api.event

import com.fasterxml.jackson.annotation.JsonIgnore
import com.itmo.microservices.demo.delivery.api.model.DeliverySubmissionOutcome
import com.itmo.microservices.demo.users.api.model.RegistrationRequest
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import ru.quipy.domain.AggregateState
import java.util.*


class UserAggregateState: AggregateState<UUID, UserAggregate> {
    private lateinit var userId: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()

    private var users = mutableMapOf<UUID, UserEntity>()
    override fun getId() = userId

    fun register(request: RegistrationRequest): UserRegisteredEvent {
        return UserRegisteredEvent(request)
    }
}

data class UserEntity(
    val id: UUID,
    val name: String,
    @JsonIgnore
    val password: String) {

    fun userDetails(): UserDetails = User(name, password, emptyList())
}


package com.itmo.microservices.demo.users.event

import com.fasterxml.jackson.annotation.JsonIgnore
import com.itmo.microservices.demo.users.api.model.RegistrationRequest
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*


class UserAggregateState: AggregateState<UUID, UserAggregate> {
    private lateinit var userId: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()

    private lateinit var userDetails: UserEntity
    override fun getId() = userId

    fun register(request: RegistrationRequest): UserRegisteredEvent {
        return UserRegisteredEvent(request)
    }

    @StateTransitionFunc
    fun register(event: UserRegisteredEvent, id: UUID = UUID.randomUUID()) {
        userId = id
        userDetails.name = event.request.name
        userDetails.password = event.request.password
    }
}

data class UserEntity(
    var name: String,
    @JsonIgnore
    var password: String) {

    fun userDetails(): UserDetails = User(name, password, emptyList())
}


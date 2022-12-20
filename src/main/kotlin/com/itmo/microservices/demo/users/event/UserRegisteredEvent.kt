package com.itmo.microservices.demo.users.event
import com.itmo.microservices.demo.users.api.model.RegistrationRequest
import ru.quipy.core.annotations.DomainEvent;
import ru.quipy.domain.Event;

const val USER_REGISTERED= "USER_REGISTERED"

@DomainEvent(name = USER_REGISTERED)
class UserRegisteredEvent (
    val request: RegistrationRequest,
    createdAt: Long = System.currentTimeMillis()

) : Event<UserAggregate>(
    name = USER_REGISTERED,
    createdAt = createdAt
)
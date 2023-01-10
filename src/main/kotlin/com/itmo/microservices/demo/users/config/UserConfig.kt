package com.itmo.microservices.demo.users.config

import com.itmo.microservices.demo.users.event.UserAggregate
import com.itmo.microservices.demo.users.event.UserAggregateState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import java.util.*

@Configuration
class UserConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun userEsService(): EventSourcingService<UUID, UserAggregate, UserAggregateState> =
        eventSourcingServiceFactory.create()
}

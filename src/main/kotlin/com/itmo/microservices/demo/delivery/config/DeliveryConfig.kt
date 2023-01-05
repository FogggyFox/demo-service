package com.itmo.microservices.demo.delivery.config

import com.itmo.microservices.demo.delivery.event.DeliveryAggregate
import com.itmo.microservices.demo.delivery.event.DeliveryAggregateState
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import java.util.*

@Configuration
class DeliveryConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun deliveryEsService(): EventSourcingService<UUID, DeliveryAggregate, DeliveryAggregateState> =
        eventSourcingServiceFactory.create()
}

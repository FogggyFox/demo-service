package com.itmo.microservices.demo.delivery.event;
import ru.quipy.core.annotations.AggregateType;
import ru.quipy.domain.Aggregate;

@AggregateType(aggregateEventsTableName = "delivery")
class DeliveryAggregate: Aggregate

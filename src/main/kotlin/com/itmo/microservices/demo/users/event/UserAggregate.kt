package com.itmo.microservices.demo.users.event;
import ru.quipy.core.annotations.AggregateType;
import ru.quipy.domain.Aggregate;

@AggregateType(aggregateEventsTableName = "users")
class UserAggregate: Aggregate
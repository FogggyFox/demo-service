package com.itmo.microservices.demo.delivery

import org.springframework.data.mongodb.core.mapping.Document
import java.util.ArrayList

@Document
object deliverySlots {
    var freeSlots: ArrayList<Int> = ArrayList<Int>(29)
}

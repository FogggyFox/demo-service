package com.itmo.microservices.demo.notifications.impl.service

import com.itmo.microservices.demo.notifications.api.service.NotificationService
import com.itmo.microservices.demo.notifications.impl.repository.NotificationUserRepository
import com.itmo.microservices.demo.notifications.impl.entity.NotificationUser
import com.itmo.microservices.demo.tasks.api.model.TaskModel
import com.itmo.microservices.demo.users.api.model.UserDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StubNotificationService(private val userRepository: NotificationUserRepository) : NotificationService {

    companion object {
        val log: Logger = LoggerFactory.getLogger(StubNotificationService::class.java)
    }

    override fun processNewUser(user: UserDTO) {
        userRepository.save(modelToEntity(user))
    }

    override fun processAssignedTask(task: TaskModel) {
        userRepository.findByIdOrNull(task.assignee)?.let {
            log.info("User ${task.assignee} (${it.email}) was assigned to task ${task.title} & should be notified (but who cares)")
        }
    }

    private fun modelToEntity(user: UserDTO): NotificationUser = NotificationUser(
    )
}
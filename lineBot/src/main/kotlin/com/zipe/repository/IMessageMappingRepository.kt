package com.zipe.repository

import com.zipe.entity.MessageMapping
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IMessageMappingRepository : CrudRepository<MessageMapping, Long> {
}

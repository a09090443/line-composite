package com.zipe.repository

import com.zipe.entity.LineMapping
import com.zipe.entity.MessageSetting
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ILineMappingRepository : CrudRepository<LineMapping, Long> {

}

package com.zipe.repository

import com.zipe.entity.LineInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ILineInfoRepository : JpaRepository<LineInfo, Long> {

    fun findByNameAndType(name: String, type: String): LineInfo

    fun findByLineIdAndType(lineId: String, type: String): LineInfo?

    @Query("SELECT DISTINCT li.lineId FROM LineInfo li WHERE li.name = :name AND li.type NOT IN (:type) ")
    fun findLineIdExcludeType(@Param("name") name: String, @Param("type") type: String): String
}

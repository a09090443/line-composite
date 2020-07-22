package com.zipe.repository

import com.zipe.entity.OrderProcess
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

const val ORDER_TREE_VIEW_PROCESS_SQL = """
SELECT
	DISTINCT op2.*
FROM
	order_process op
INNER JOIN order_process op2 ON
	op.process_id = op2.parent_id
	OR op.process_id = op2.process_id
WHERE
	op.process_name = :processName AND op.line_id = :lineId
ORDER BY
	op2.`sequence`
"""

@Repository
interface IOrderProcessRepository : CrudRepository<OrderProcess, Long> {

    @Query(ORDER_TREE_VIEW_PROCESS_SQL, nativeQuery = true)
    fun findByProcessNameAndLineIdOrderBySequence(@Param("processName") processName: String, @Param("lineId") lineId: Long): List<OrderProcess>

    fun findByProcessName(name: String): OrderProcess
}

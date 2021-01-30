package com.zipe.jdbc

import com.zipe.enum.ResourceEnum
import com.zipe.jdbc.criteria.Conditions
import com.zipe.jdbc.criteria.Paging
import com.zipe.util.file.FileUtil
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ResourceLoader
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport
import org.springframework.stereotype.Repository


/**
 * 處理JDBC操作共用工具 基礎類別
 *
 * @author adam.yeh
 */
@Repository
abstract class BaseJDBC {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    lateinit var resourceLoader: ResourceLoader

    @Autowired
    lateinit var support: NamedParameterJdbcDaoSupport

    /**
     *
     * @param resource
     * @return
     */
    fun update(resource: ResourceEnum): Int {
        return support.jdbcTemplate!!.update(getSqlText(resource))
    }

    /**
     *
     * @param resource
     * @param sqlParams
     * @return
     */
    fun update(resource: ResourceEnum, sqlParams: Map<String?, Any?>?): Int {
        return support.namedParameterJdbcTemplate!!.update(getSqlText(resource), sqlParams!!)
    }

    /**
     *
     * @param resource
     * @param sqlParam
     * @return
     */
    fun updateBatch(resource: ResourceEnum, sqlParam: List<Map<String?, Any?>?>): IntArray {
        val mapParamArr = arrayOfNulls<MapSqlParameterSource>(sqlParam.size)
        for (i in sqlParam.indices) {
            val m = sqlParam[i]
            mapParamArr[i] = MapSqlParameterSource(m)
        }
        return support.namedParameterJdbcTemplate!!.batchUpdate(getSqlText(resource), mapParamArr)
    }

    /**
     *
     * @param resource
     * @param clazz
     * @return
     */
    fun <T> queryForBean(resource: ResourceEnum, clazz: Class<T>): T? {
        return queryForBean(resource, HashMap(), clazz)
    }

    /**
     *
     * @param resource
     * @param clazz
     * @return
     */
    fun <T> queryForBean(resource: ResourceEnum, conditions: Conditions?, clazz: Class<T>?): T? {
        return support.namedParameterJdbcTemplate!!.query(
            getSqlText(resource, conditions),
            HashMap<String, Any?>(),
            BeanPropertyRowMapper(clazz!!)
        ).getOrNull(0)
    }

    /**
     *
     * @param resource
     * @param params
     * @param clazz
     * @return
     */
    fun <T> queryForBean(resource: ResourceEnum, params: Map<String, Any>, clazz: Class<T>): T? {
        return support.namedParameterJdbcTemplate!!.query(getSqlText(resource), params, BeanPropertyRowMapper(clazz))
            .getOrNull(0)
    }

    /**
     *
     * @param resource
     * @param clazz
     * @return
     */
    fun <T> queryForBean(
        resource: ResourceEnum,
        conditions: Conditions?,
        params: Map<String?, Any?>?,
        clazz: Class<T>?
    ): T? {
        val dataLs = support.namedParameterJdbcTemplate!!.query(
            getSqlText(resource, conditions),
            params!!,
            BeanPropertyRowMapper(clazz!!)
        )
        return if (CollectionUtils.isEmpty(dataLs)) null else dataLs[0]
    }

    /**
     *
     * @param resource
     * @return
     */
    fun queryForMap(resource: ResourceEnum): Map<String, Any> {
        return queryForMap(resource, mapOf())
    }

    /**
     *
     * @param resource
     * @param conditions
     * @return
     */
    fun queryForMap(resource: ResourceEnum, conditions: Conditions): Map<String, Any>? {
        val dataLs =
            support.namedParameterJdbcTemplate!!.queryForList(getSqlText(resource, conditions), HashMap<String, Any?>())
        val size = dataLs.size
        if (dataLs.size > 1) {
            throw IncorrectResultSizeDataAccessException(1, size)
        }
        return if (size == 1) {
            dataLs[0]
        } else null
    }

    /**
     *
     * @param resource
     * @param params    查詢條件對應參數
     * @return
     */
    fun queryForMap(resource: ResourceEnum, params: Map<String, Any>): Map<String, Any> {
        val dataLs = support.namedParameterJdbcTemplate!!.queryForList(getSqlText(resource), params)
        val size = dataLs.size
        if (dataLs.size > 1) {
            throw IncorrectResultSizeDataAccessException(1, size)
        }
        return if (size == 1) {
            dataLs[0]
        } else mapOf()
    }

    /**
     *
     * @param resource
     * @param params    查詢條件對應參數
     * @return
     */
    fun queryForMap(resource: ResourceEnum, conditions: Conditions?, params: Map<String?, Any?>?): Map<String, Any>? {
        val dataLs = support.namedParameterJdbcTemplate!!.queryForList(getSqlText(resource, conditions), params!!)
        val size = dataLs.size
        if (dataLs.size > 1) {
            throw IncorrectResultSizeDataAccessException(1, size)
        }
        return if (size == 1) {
            dataLs[0]
        } else null
    }

    /**
     *
     * @param resource
     * @return
     */
    fun <T> queryForList(resource: ResourceEnum): List<Map<String, Any>> {
        return support.namedParameterJdbcTemplate!!.queryForList(getSqlText(resource), HashMap<String, Any?>())
    }

    /**
     *
     * @param resource
     * @param conditions
     * @return
     */
    fun <T> queryForList(resource: ResourceEnum, conditions: Conditions?): List<Map<String, Any>> {
        return support.namedParameterJdbcTemplate!!.queryForList(
            getSqlText(resource, conditions),
            HashMap<String, Any?>()
        )

    }

    /**
     *
     * @param resource
     * @param params    查詢條件參數
     * @return
     */
    fun <T> queryForList(resource: ResourceEnum, params: Map<String?, Any?>?): List<Map<String, Any>> {
        return support.namedParameterJdbcTemplate!!.queryForList(getSqlText(resource), params!!)
    }

    /**
     * @param resource
     * @param conditions
     * @param params    查詢條件參數
     * @return
     */
    fun <T> queryForList(
        resource: ResourceEnum,
        conditions: Conditions?,
        params: Map<String?, Any?>?
    ): List<Map<String, Any>> {
        return support.namedParameterJdbcTemplate!!.queryForList(getSqlText(resource, conditions), params!!)
    }

    /**
     * @param resource
     * @param clazz     須轉型之類別
     * @return
     */
    fun <T> queryForList(resource: ResourceEnum, clazz: Class<T>): List<T> {
        return support.namedParameterJdbcTemplate!!.query(
            getSqlText(resource),
            HashMap<String, Any?>(),
            BeanPropertyRowMapper(clazz)
        )
    }

    /**
     * @param resource
     * @param clazz     須轉型之類別
     * @return
     */
    fun <T> queryForList(resource: ResourceEnum, paging: Paging, clazz: Class<T>?): List<T> {
        return support.namedParameterJdbcTemplate!!.query(
            getSqlText(resource, null, paging),
            HashMap<String, Any?>(),
            BeanPropertyRowMapper(clazz!!)
        )
    }

    /**
     * @param resource
     * @param conditions
     * @param clazz     須轉型之類別
     * @return
     */
    fun <T> queryForList(resource: ResourceEnum, conditions: Conditions?, clazz: Class<T>?): List<T> {
        val rtnLs = support.namedParameterJdbcTemplate!!.query(
            getSqlText(resource, conditions),
            HashMap<String, Any?>(),
            BeanPropertyRowMapper(clazz!!)
        )
        return if (CollectionUtils.isEmpty(rtnLs)) {
            emptyList()
        } else rtnLs
    }

    /**
     * @param resource
     * @param conditions
     * @param clazz     須轉型之類別
     * @return
     */
    fun <T> queryForList(resource: ResourceEnum, conditions: Conditions?, paging: Paging, clazz: Class<T>?): List<T> {
        val rtnLs = support.namedParameterJdbcTemplate!!.query(
            getSqlText(resource, conditions, paging),
            HashMap<String, Any?>(),
            BeanPropertyRowMapper(clazz!!)
        )
        return if (CollectionUtils.isEmpty(rtnLs)) {
            emptyList()
        } else rtnLs
    }

    /**
     * @param resource
     * @param conditions
     * @param params    查詢條件參數
     * @param clazz     須轉型之類別
     * @return
     */
    fun <T> queryForList(
        resource: ResourceEnum,
        conditions: Conditions?,
        params: Map<String, Any>?,
        clazz: Class<T>?
    ): List<T> {
        return support.namedParameterJdbcTemplate!!.query(
            getSqlText(resource, conditions),
            params!!,
            BeanPropertyRowMapper(clazz!!)
        )
    }

    /**
     * @param resource
     * @param conditions
     * @param params    查詢條件參數
     * @param clazz     須轉型之類別
     * @return
     */
    fun <T> queryForList(
        resource: ResourceEnum,
        conditions: Conditions?,
        params: Map<String?, Any?>?,
        paging: Paging,
        clazz: Class<T>?
    ): List<T> {
        return support.namedParameterJdbcTemplate!!.query(
            getSqlText(resource, conditions, paging),
            params!!,
            BeanPropertyRowMapper(clazz!!)
        )
    }

    /**
     *
     * @param resource
     * @param params    查詢條件參數
     * @param clazz     須轉型之類別
     * @return
     */
    fun <T> queryForList(resource: ResourceEnum, params: Map<String?, Any?>?, clazz: Class<T>?): List<T> {
        return support.namedParameterJdbcTemplate!!.query(
            getSqlText(resource),
            params!!,
            BeanPropertyRowMapper(clazz!!)
        )

    }

    /**
     *
     * @param resource
     * @param params    查詢條件參數
     * @param clazz     須轉型之類別
     * @return
     */
    fun <T> queryForList(
        resource: ResourceEnum,
        params: Map<String?, Any?>?,
        paging: Paging,
        clazz: Class<T>?
    ): List<T> {
        return support.namedParameterJdbcTemplate!!.query(
            getSqlText(resource, null, paging),
            params!!,
            BeanPropertyRowMapper(clazz!!)
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(BaseJDBC::class.java)
        private fun getSqlText(resource: ResourceEnum): String {
            return getSqlText(resource, null)
        }

        @Synchronized
        private fun getSqlText(resource: ResourceEnum, conditions: Conditions?): String {
            val path = StringBuilder()
            path.append(resource.dir())
            path.append(resource.file())
            path.append(resource.extension())
            val sqlFile = ClassPathResource(path.toString()).file

            var sqlText: String? = FileUtil.readTxtFile(sqlFile)
            if (conditions != null) {
                sqlText = conditions.done(sqlText ?: "")
            }
            logger.info("Use SQL : " + resource.file())
            return sqlText ?: ""
        }

        private fun getSqlText(resource: ResourceEnum, conditions: Conditions?, paging: Paging): String {
            var template: String? = paging.getPagingSQL()
            val sqlText = getSqlText(resource, conditions)
            val ended: String = java.lang.String.valueOf(paging.page * paging.pagesize)
            val start: String = java.lang.String.valueOf(paging.page * paging.pagesize - 9)
            val orderBy: String = StringUtils.join(paging.orderBy, ",")
            if (StringUtils.isBlank(orderBy)) {
                try {
                    template = ""
                    throw NullPointerException("The parameter from Paging.orderBy is null.")
                } catch (e: Exception) {
                    logger.error(e.message, e)
                }
            }
            template = StringUtils.replace(template, "\${START}", start)
            template = StringUtils.replace(template, "\${ENDED}", ended)
            template = StringUtils.replace(template, "\${ORDER_BY}", orderBy)
            template = StringUtils.replace(template, "\${QUERY_STRING}", sqlText)
            return template
        }
    }
}

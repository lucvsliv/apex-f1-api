package com.lucvs.apex_f1_api.infrastructure.persistence.type

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.usertype.UserType
import java.io.Serializable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

/**
 * Hibernate 6용 Custom Vector Type
 * DB의 vector 타입(PGobject) <-> kotlin의 FloatArray 변환
 */
class VectorType : UserType<FloatArray> {

    override fun getSqlType(): Int = Types.OTHER

    override fun returnedClass(): Class<FloatArray> = FloatArray::class.java

    override fun equals(x: FloatArray?, y: FloatArray?): Boolean {
        return x.contentEquals(y)
    }

    override fun hashCode(x: FloatArray?): Int {
        return x.contentHashCode()
    }

    override fun nullSafeGet(
        rs: ResultSet,
        position: Int,
        session: SharedSessionContractImplementor?,
        owner: Any?
    ): FloatArray? {
        val pgObject = rs.getObject(position) ?: return null
        val vectorString = pgObject.toString()

        // "[1.0,2.0,3.0]" 형식의 문자열을 FloatArray로 변환
        return vectorString
            .removePrefix("[")
            .removeSuffix("]")
            .split(",")
            .map { it.trim().toFloat() }
            .toFloatArray()
    }

    override fun nullSafeSet(
        st: PreparedStatement,
        value: FloatArray?,
        index: Int,
        session: SharedSessionContractImplementor?
    ) {
        if (value == null) {
            st.setNull(index, Types.OTHER)
        } else {
            val vectorString = value.joinToString(
                separator = ",",
                prefix = "[",
                postfix = "]"
            )
            val pgObject = org.postgresql.util.PGobject()
            pgObject.type = "vector"
            pgObject.value = vectorString
            st.setObject(index, pgObject)
        }
    }

    override fun deepCopy(value: FloatArray?): FloatArray? {
        return value?.copyOf()
    }

    override fun isMutable(): Boolean = true

    override fun disassemble(value: FloatArray?): Serializable? {
        return deepCopy(value)
    }

    override fun assemble(cached: Serializable?, owner: Any?): FloatArray? {
        return deepCopy(cached as FloatArray?)
    }
}
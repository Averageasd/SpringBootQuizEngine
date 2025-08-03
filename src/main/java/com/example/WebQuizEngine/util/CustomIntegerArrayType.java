package com.example.WebQuizEngine.util;

import org.hibernate.Incubating;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;

public class CustomIntegerArrayType implements UserType<Integer[]> {
    @Override
    public int getSqlType() {
        return Types.ARRAY;
    }

    @Override
    public Class<Integer[]> returnedClass() {
        return Integer[].class;
    }

    @Override
    public boolean equals(Integer[] integers, Integer[] j1) {
        return false;
    }

    @Override
    public int hashCode(Integer[] integers) {
        return 0;
    }

    @Override
    public Integer[] nullSafeGet(ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        Array array = resultSet.getArray(i);
        return array != null ? (Integer[]) array.getArray() : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Integer[] integers, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        if (preparedStatement != null) {
            if (integers != null) {
                Array array = sharedSessionContractImplementor
                        .getJdbcConnectionAccess()
                        .obtainConnection()
                        .createArrayOf("int", integers);
                preparedStatement.setArray(i, array);
            } else {
                preparedStatement.setNull(i, Types.ARRAY);
            }
        }
    }

    @Override
    public Integer[] deepCopy(Integer[] integers) {
        return new Integer[0];
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Integer[] integers) {
        return null;
    }

    @Override
    public Integer[] assemble(Serializable serializable, Object o) {
        return new Integer[0];
    }
}

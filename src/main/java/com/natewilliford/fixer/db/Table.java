package com.natewilliford.fixer.db;

import java.sql.Connection;
import java.sql.SQLException;

abstract class Table {

    abstract String getTableName();
    abstract int getVersion();
    abstract void create(Connection connection) throws SQLException;
    abstract void upgrade(Connection connection, int version) throws SQLException;
}

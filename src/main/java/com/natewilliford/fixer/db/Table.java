package com.natewilliford.fixer.db;

import java.sql.Connection;
import java.sql.SQLException;

abstract class Table {

    protected final Connection connection;

    abstract String getTableName();
    abstract void create() throws SQLException;

    Table(Connection connection) {
        this.connection = connection;
    }
}

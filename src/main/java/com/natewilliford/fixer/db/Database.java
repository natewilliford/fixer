package com.natewilliford.fixer.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private static final String DB_NAME = "dev.db";
    private Connection c = null;

    public Database() {}

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void create() {

    }
}

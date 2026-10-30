package codequest;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try (Connection con = DB.getConnection()) {
            System.out.println("Database connected successfully!");
        } catch (Exception e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}

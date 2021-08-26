package mhl.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        System.out.println("enter a int");
        int i = Utility.readInt();
        System.out.println("i=" +i);

        Connection connection = JDBCUtilsByDruid.getConnection();
        System.out.println(connection);

    }
}

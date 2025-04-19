package com.algosec.registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseUtils {

    public static void checkDataInDatabase() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // חיבור למסד הנתונים
            conn = DriverManager.getConnection("jdbc:sqlite:path_to_your_database.db");

            // יצירת Statement
            stmt = conn.createStatement();

            // חיפוש נתונים בטבלה
            String sql = "SELECT * FROM users WHERE email = 'johndoe@example.com';";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                // הדפסת הנתונים
                String name = rs.getString("name");
                String email = rs.getString("email");

                System.out.println("הנתונים שנשמרו: " + name + ", " + email);
            } else {
                System.out.println("לא נמצאו נתונים");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

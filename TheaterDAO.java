package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TheaterDAO {

    public static void viewTheaters() {

        String sql = "SELECT * FROM theaters";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            System.out.println("\n========== THEATER LIST ==========");

            while (rs.next()) {

                System.out.println(
                        "Theater ID   : " + rs.getInt("theater_id")
                );

                System.out.println(
                        "Theater Name : " + rs.getString("theater_name")
                );

                System.out.println(
                        "City         : " + rs.getString("city")
                );

                System.out.println("----------------------------------");
            }

        } catch (SQLException e) {

            System.out.println("Error while fetching theaters.");
            e.printStackTrace();
        }
    }
}

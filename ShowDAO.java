package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowDAO {

    public static void viewShows() {

        String sql =
                "SELECT " +
                "s.show_id, " +
                "m.movie_name, " +
                "t.theater_name, " +
                "t.city, " +
                "s.show_time, " +
                "s.available_seats " +
                "FROM shows s " +
                "JOIN movies m ON s.movie_id = m.movie_id " +
                "JOIN theaters t ON s.theater_id = t.theater_id";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            System.out.println("\n========== SHOW LIST ==========");

            while (rs.next()) {

                System.out.println(
                        "Show ID         : " + rs.getInt("show_id")
                );

                System.out.println(
                        "Movie           : " + rs.getString("movie_name")
                );

                System.out.println(
                        "Theater         : " + rs.getString("theater_name")
                );

                System.out.println(
                        "City            : " + rs.getString("city")
                );

                System.out.println(
                        "Show Time       : " + rs.getTimestamp("show_time")
                );

                System.out.println(
                        "Available Seats : " +
                        rs.getInt("available_seats")
                );

                System.out.println("--------------------------------");
            }

        } catch (SQLException e) {

            System.out.println("Error while fetching shows.");
            e.printStackTrace();
        }
    }
}

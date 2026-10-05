package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDAO {

    public static void viewMovies() {

        String sql = "SELECT * FROM movies";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            System.out.println("\n========== MOVIE LIST ==========");

            while (rs.next()) {

                System.out.println(
                        "Movie ID   : " + rs.getInt("movie_id")
                );

                System.out.println(
                        "Movie Name : " + rs.getString("movie_name")
                );

                System.out.println(
                        "Language   : " + rs.getString("language")
                );

                System.out.println(
                        "Duration   : " + rs.getInt("duration") + " minutes"
                );

                System.out.println("--------------------------------");
            }

        } catch (SQLException e) {

            System.out.println("Error while fetching movies.");
            e.printStackTrace();
        }
    }
  
}

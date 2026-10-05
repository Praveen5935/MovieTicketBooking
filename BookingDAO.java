package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingDAO {

    private static final double TICKET_PRICE = 200.00;

    public static void bookTicket(
            int showId,
            String customerName,
            int seats
    ) {

        if (showId <= 0) {

            System.out.println("Invalid Show ID.");
            return;
        }

        if (customerName == null ||
                customerName.trim().isEmpty()) {

            System.out.println("Customer name cannot be empty.");
            return;
        }

        if (seats <= 0) {

            System.out.println(
                    "Number of seats must be greater than 0."
            );

            return;
        }

        String checkSQL =
                "SELECT available_seats " +
                "FROM shows " +
                "WHERE show_id = ? " +
                "FOR UPDATE";

        String bookingSQL =
                "INSERT INTO bookings " +
                "(show_id, customer_name, seats_booked, total_amount) " +
                "VALUES (?, ?, ?, ?)";

        String updateSQL =
                "UPDATE shows " +
                "SET available_seats = available_seats - ? " +
                "WHERE show_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            // Start transaction
            con.setAutoCommit(false);

            try {

                int availableSeats;

                // Step 1: Check available seats
                try (PreparedStatement ps =
                             con.prepareStatement(checkSQL)) {

                    ps.setInt(1, showId);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (!rs.next()) {

                            System.out.println(
                                    "Show not found."
                            );

                            con.rollback();
                            return;
                        }

                        availableSeats =
                                rs.getInt("available_seats");
                    }
                }

                // Step 2: Validate seats
                if (seats > availableSeats) {

                    System.out.println(
                            "Booking failed!"
                    );

                    System.out.println(
                            "Available seats: " +
                            availableSeats
                    );

                    con.rollback();
                    return;
                }

                // Step 3: Calculate amount
                double totalAmount =
                        seats * TICKET_PRICE;

                // Step 4: Insert booking
                int bookingId;

                try (
                        PreparedStatement ps =
                                con.prepareStatement(
                                        bookingSQL,
                                        Statement.RETURN_GENERATED_KEYS
                                )
                ) {

                    ps.setInt(1, showId);
                    ps.setString(2, customerName);
                    ps.setInt(3, seats);
                    ps.setDouble(4, totalAmount);

                    ps.executeUpdate();

                    try (ResultSet rs =
                                 ps.getGeneratedKeys()) {

                        if (rs.next()) {

                            bookingId =
                                    rs.getInt(1);

                        } else {

                            throw new SQLException(
                                    "Booking ID could not be generated."
                            );
                        }
                    }
                }

                // Step 5: Update available seats
                try (PreparedStatement ps =
                             con.prepareStatement(updateSQL)) {

                    ps.setInt(1, seats);
                    ps.setInt(2, showId);

                    int rows =
                            ps.executeUpdate();

                    if (rows == 0) {

                        throw new SQLException(
                                "Seat update failed."
                        );
                    }
                }

                // Step 6: Commit transaction
                con.commit();

                // Ticket generation
                System.out.println("\n========== TICKET ==========");

                System.out.println(
                        "Booking ID    : " + bookingId
                );

                System.out.println(
                        "Customer Name : " + customerName
                );

                System.out.println(
                        "Show ID       : " + showId
                );

                System.out.println(
                        "Seats Booked  : " + seats
                );

                System.out.println(
                        "Ticket Price  : ₹" + TICKET_PRICE
                );

                System.out.println(
                        "Total Amount  : ₹" + totalAmount
                );

                System.out.println(
                        "Status        : CONFIRMED"
                );

                System.out.println(
                        "============================"
                );

            } catch (SQLException e) {

                // Rollback if anything fails
                con.rollback();

                System.out.println(
                        "Booking failed. Transaction rolled back."
                );

                e.printStackTrace();
            }

        } catch (SQLException e) {

            System.out.println(
                    "Database error occurred."
            );

            e.printStackTrace();
        }
    }
}

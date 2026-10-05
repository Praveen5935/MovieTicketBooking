package jdbc;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("   MOVIE TICKET BOOKING SYSTEM");
            System.out.println("=================================");

            System.out.println("1. View Movies");
            System.out.println("2. View Theaters");
            System.out.println("3. View Shows");
            System.out.println("4. Book Ticket");
            System.out.println("5. View All Bookings");
            System.out.println("6. Search Booking");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {

                System.out.println(
                        "Please enter a valid number."
                );

                sc.next();
                System.out.print("Enter your choice: ");
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    MovieDAO.viewMovies();

                    break;

                case 2:

                    TheaterDAO.viewTheaters();

                    break;

                case 3:

                    ShowDAO.viewShows();

                    break;

                case 4:

                    System.out.print(
                            "Enter Show ID: "
                    );

                    while (!sc.hasNextInt()) {

                        System.out.println(
                                "Enter a valid Show ID."
                        );

                        sc.next();
                    }

                    int showId = sc.nextInt();
                    sc.nextLine();

                    System.out.print(
                            "Enter Customer Name: "
                    );

                    String customerName =
                            sc.nextLine();

                    System.out.print(
                            "Enter Number of Seats: "
                    );

                    while (!sc.hasNextInt()) {

                        System.out.println(
                                "Enter a valid number of seats."
                        );

                        sc.next();
                    }

                    int seats = sc.nextInt();
                    sc.nextLine();

                    BookingDAO.bookTicket(
                            showId,
                            customerName,
                            seats
                    );

                    break;

                case 5:

                    BookingDAO.viewBookings();

                    break;

                case 6:

                    System.out.print(
                            "Enter Booking ID: "
                    );

                    while (!sc.hasNextInt()) {

                        System.out.println(
                                "Enter a valid Booking ID."
                        );

                        sc.next();
                    }

                    int bookingId =
                            sc.nextInt();

                    sc.nextLine();

                    BookingDAO.searchBooking(
                            bookingId
                    );

                    break;

                case 7:

                    System.out.println(
                            "Thank you for using Movie Ticket Booking System!"
                    );

                    break;

                default:

                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }

        } while (choice != 7);

        sc.close();
    }
}

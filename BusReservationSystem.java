import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BusReservationSystem {

    private static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    private static class Reservation {
        private String source;
        private String destination;
        private int seats;

        public Reservation(String source, String destination, int seats) {
            this.source = source;
            this.destination = destination;
            this.seats = seats;
        }

        public String getSource() {
            return source;
        }

        public String getDestination() {
            return destination;
        }

        public int getSeats() {
            return seats;
        }
    }

    private static class LoginController {
        public boolean authenticate(User user) {
            String jdbcUrl = "jdbc:mysql://localhost:3306/bus_reservation";
            String dbUser = "root";
            String dbPassword = "036529";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String query = "SELECT * FROM users WHERE username=? AND password=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, user.getPassword());

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        return resultSet.next(); // If a row is found, authentication is successful
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Print the exception for debugging
                return false;
            }
        }
    }

    private static class ReservationController {
        public void createReservation(Reservation reservation) {
            // Replace this with actual reservation logic
            System.out.println("Created reservation: " + reservation.getSource() + " -> " +
                    reservation.getDestination() + ", " + reservation.getSeats() + " seats");

            // Generate and print the ticket
            printTicket(reservation);
        }

        private void printTicket(Reservation reservation) {
            // You can customize the ticket format here
            String ticket = "--------- Ticket ---------\n" +
                    "Source: " + reservation.getSource() + "\n" +
                    "Destination: " + reservation.getDestination() + "\n" +
                    "Seats: " + reservation.getSeats() + "\n" +
                    "-------------------------";

            JOptionPane.showMessageDialog(null, ticket, "Ticket Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static class BusReservationView extends JFrame {
        private JLabel sourceLabel, destinationLabel, seatsLabel;
        private JTextField sourceField, destinationField, seatsField;
        private JButton reserveButton;

        public BusReservationView() {
            createComponents();
            createLayout();
            setTitle("Bus Reservation System");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void createComponents() {
            sourceLabel = new JLabel("Source:");
            destinationLabel = new JLabel("Destination:");
            seatsLabel = new JLabel("Seats:");

            sourceField = new JTextField(15);
            destinationField = new JTextField(15);
            seatsField = new JTextField(5);

            reserveButton = new JButton("Reserve");
            reserveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String source = sourceField.getText();
                    String destination = destinationField.getText();
                    int seats = Integer.parseInt(seatsField.getText());

                    Reservation reservation = new Reservation(source, destination, seats);
                    ReservationController reservationController = new ReservationController();
                    reservationController.createReservation(reservation);

                    JOptionPane.showMessageDialog(BusReservationView.this, "Reservation successful!");
                }
            });
        }

        private void createLayout() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(5, 5, 5, 5);

            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(sourceLabel, constraints);

            constraints.gridx = 1;
            panel.add(sourceField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 1;
            panel.add(destinationLabel, constraints);

            constraints.gridx = 1;
            panel.add(destinationField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 2;
            panel.add(seatsLabel, constraints);

            constraints.gridx = 1;
            panel.add(seatsField, constraints);

            constraints.gridx = 1;
            constraints.gridy = 3;
            panel.add(reserveButton, constraints);

            add(panel);
        }
    }

    private static class LoginView extends JFrame {
        private JLabel usernameLabel, passwordLabel;
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton loginButton;

        public LoginView() {
            createComponents();
            createLayout();
            setTitle("Login");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setResizable(false);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void createComponents() {
            usernameLabel = new JLabel("Username:");
            passwordLabel = new JLabel("Password:");

            usernameField = new JTextField(15);
            passwordField = new JPasswordField(15);

            loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    User user = new User(username, password);
                    LoginController loginController = new LoginController();

                    if (loginController.authenticate(user)) {
                        JOptionPane.showMessageDialog(LoginView.this, "Login successful!");
                        // Proceed to the bus reservation view
                        new BusReservationView();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginView.this, "Invalid credentials. Please try again.");
                    }
                }
            });
        }

        private void createLayout() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(5, 5, 5, 5);

            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(usernameLabel, constraints);

            constraints.gridx = 1;
            panel.add(usernameField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 1;
            panel.add(passwordLabel, constraints);

            constraints.gridx = 1;
            panel.add(passwordField, constraints);

            constraints.gridx = 1;
            constraints.gridy = 2;
            panel.add(loginButton, constraints);

            add(panel);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginView::new);
    }
}

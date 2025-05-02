package hotel.databaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import hotel.classes.Booking;
import hotel.classes.Order;

public class BookingDb {

    Connection conn;
    PreparedStatement statement = null;
    ResultSet result = null;

    public BookingDb() {
        conn = DataBaseConnection.connectTODB();
    }

    // public void insertBooking(Booking booking) {
    //     for (int i = 0; i < booking.getRooms().size(); i++) {
    //         try {
    //             String insertQuery = "insert into booking"
    //                     + "('customer_id','booking_room','guests','check_in','check_out','booking_type','has_checked_out')"
    //                     + " values("
    //                     + booking.getCustomer().getCustomerId()
    //                     + ",'" + booking.getRooms().get(i).getRoomNo() + "'"
    //                     + "," + booking.getPerson() + ""
    //                     + "," + booking.getCheckInDateTime() + ""
    //                     + "," + booking.getCheckOutDateTime() + ""
    //                     + ",'" + booking.getBookingType() + "',"
    //                     + 0
    //                     + " )";

    //             // ^^^ 0 for has_checked_out
    //             statement = conn.prepareStatement(insertQuery);
    //             //System.out.println(">>>>>>>>>> " + insertQuery);
    //             statement.execute();

    //             JOptionPane.showMessageDialog(null, "successfully inserted new Booking");

    //         } catch (SQLException ex) {
    //             JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "InsertQuery  booking Failed");
    //         } finally {
    //             flushStatementOnly();
    //         }
    //     }

    // }

    public void insertBooking(Booking booking) {
        try {
            String insertQuery = "INSERT INTO booking "
                    + "(customer_id, booking_room, guests, check_in, check_out, booking_type, has_checked_out) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
            for (int i = 0; i < booking.getRooms().size(); i++) {
                // Préparation de la requête avec des paramètres sécurisés
                statement = conn.prepareStatement(insertQuery);
    
                // Remplacement des paramètres par des valeurs sécurisées
                statement.setInt(1, booking.getCustomer().getCustomerId()); // customer_id
                statement.setString(2, booking.getRooms().get(i).getRoomNo()); // booking_room
                statement.setInt(3, booking.getPerson()); // guests

                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                 // check_in
                // Conversion de check-in
                LocalDateTime checkInDateTime = LocalDateTime.parse(booking.getCheckInDateTime(), formatter);
                statement.setTimestamp(4, Timestamp.valueOf(checkInDateTime));
                 // check_out
                // Conversion de check-out
                LocalDateTime checkOutDateTime = LocalDateTime.parse(booking.getCheckOutDateTime(), formatter);
                statement.setTimestamp(5, Timestamp.valueOf(checkOutDateTime));
                statement.setString(6, booking.getBookingType()); // booking_type
                statement.setInt(7, 0); // has_checked_out = 0
    
                // Exécution de la requête
                statement.execute();
    
                JOptionPane.showMessageDialog(null, "Successfully inserted new booking");
    
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\nInsert query booking failed");
        } finally {
            flushStatementOnly();
        }
    }
    

    public ResultSet getBookingInformation() {
        try {
            String query = "select * from booking";
            statement = conn.prepareStatement(query);
            result = statement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning all booking DB Operation");
        }

        return result;
    }

    // public ResultSet getABooking(int bookingId) {
    //     try {
    //         String query = "select * from booking where booking_id = " + bookingId;
    //         statement = conn.prepareStatement(query);
    //         result = statement.executeQuery();
    //     } catch (SQLException ex) {
    //         JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning A booking DB Operation");
    //     }

    //     return result;
    // }

    public ResultSet getABooking(int bookingId) {
        try {
            String query = "SELECT * FROM booking WHERE booking_id = ?";
            statement = conn.prepareStatement(query);
    
            // Remplacer le paramètre par la valeur sécurisée
            statement.setInt(1, bookingId);
    
            result = statement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning A booking DB Operation");
        }
        return result;
    }
    

    public ResultSet bookingsReadyForOrder(String roomName) {
        try {
           
            // String query = "select booking_id,booking_room,name from booking join userInfo on booking.customer_id = userInfo.user_id where booking_room like '%" + roomName + "%' and has_checked_out = 0 order by booking_id desc";
            // System.out.println(query);
            // statement = conn.prepareStatement(query);
            // result = statement.executeQuery();
            String query = "SELECT booking_id, booking_room, name FROM booking " +
               "JOIN userInfo ON booking.customer_id = userInfo.user_id " +
               "WHERE booking_room LIKE ? AND has_checked_out = 0 " +
               "ORDER BY booking_id DESC";

            PreparedStatement statement = conn.prepareStatement(query);

            // Remplacement du paramètre par la valeur sécurisée
            statement.setString(1, "%" + roomName + "%");

            ResultSet result = statement.executeQuery();


        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning bookingsReadyForOrder method,BookingDb");
        }

        return result;
    }

    public void updateCheckOut(int bookingId, long checkOutTime) {
        try {
            // String updateFood = "update booking set has_checked_out= 1, check_out = " + checkOutTime + " where booking_id = " + bookingId;

            // statement = conn.prepareStatement(updateFood);

            // statement.execute();

            // JOptionPane.showMessageDialog(null, "successfully update Check Out ");
            String updateFood = "UPDATE booking SET has_checked_out = 1, check_out = ? WHERE booking_id = ?";

            PreparedStatement statement = conn.prepareStatement(updateFood);

            // Remplacement des paramètres par des valeurs sécurisées
            statement.setLong(1, checkOutTime);  // Si checkOutTime est un long
            statement.setInt(2, bookingId);      // Si bookingId est un entier

            statement.executeUpdate();  // Utilisation de executeUpdate pour les requêtes de mise à jour
            JOptionPane.showMessageDialog(null, "successfully updated Check Out");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "updateCheckOut of BookingDB Failed");
        } finally {
            flushStatementOnly();
        }
    }

    public int getRoomPrice(int bookingId) {

        int price = -1;
        try {

            // String query = "select price from booking join room on booking_room = room_no join roomType on type= room_class where booking_id=" + bookingId;
            // System.out.println(query);
            // statement = conn.prepareStatement(query);
            // result = statement.executeQuery();
            // price = result.getInt("price");
           
            // System.out.println(price);
            // flushAll();
            String query = "SELECT price FROM booking " +
               "JOIN room ON booking_room = room_no " +
               "JOIN roomType ON type = room_class " +
               "WHERE booking_id = ?";

            PreparedStatement statement = conn.prepareStatement(query);

            // Remplacer le paramètre par la valeur sécurisée de bookingId
            statement.setInt(1, bookingId);

            ResultSet result = statement.executeQuery();

            // Si un résultat est trouvé, on récupère le prix
            if (result.next()) {
                price = result.getInt("price");
            }

            flushAll();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning price getRoomPrice,bookingDB");
        }

        return price;
    }
    
    public void insertOrder(Order order) {
    //     try {
    //         String insertOrder = "insert into orderItem('booking_id','item_food','price','quantity','total') values(" + order.getBookingId() + ",'" + order.getFoodItem() + "'," + order.getPrice() + "," + order.getQuantity() + "," + order.getTotal() + ")";

    //         statement = conn.prepareStatement(insertOrder);
    //         System.out.println(">>>>>>>>>> " + insertOrder);
    //         statement.execute();

    //         JOptionPane.showMessageDialog(null, "successfully inserted a new Order");

    //     } catch (SQLException ex) {
    //         JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "Order Failed");
    //     } finally {
    //         flushStatementOnly();
    //     }
    String insertOrder = "INSERT INTO orderItem (booking_id, item_food, price, quantity, total) VALUES (?, ?, ?, ?, ?)";

try (PreparedStatement statement = conn.prepareStatement(insertOrder)) {
    statement.setInt(1, order.getBookingId());     // Remplacer par le bon champ
    statement.setString(2, order.getFoodItem());   // Remplacer par le bon champ
    statement.setDouble(3, order.getPrice());      // Remplacer par le bon champ
    statement.setInt(4, order.getQuantity());      // Remplacer par le bon champ
    statement.setDouble(5, order.getTotal());      // Remplacer par le bon champ

    System.out.println("Executing: " + statement.toString());

    statement.executeUpdate();
} catch (SQLException ex) {
    ex.printStackTrace();
}
    finally {
        flushStatementOnly();
    }
    }
    public ResultSet getAllPaymentInfo(int bookingId)
    {
        try {

            // String query = "select * from orderItem where booking_id=" + bookingId;
            // System.out.println(query);
            // statement = conn.prepareStatement(query);
            // result = statement.executeQuery();
            String query = "SELECT * FROM orderItem WHERE booking_id = ?";

            PreparedStatement statement = conn.prepareStatement(query);

            // Remplace le paramètre par la valeur sécurisée
            statement.setInt(1, bookingId);

            ResultSet result = statement.executeQuery();

          

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning payment getAllPaymentInfo,bookingDB");
        }
        return result;
    }

    public void flushAll() {
        {
            try {
                statement.close();
                result.close();
            } catch (SQLException ex) {
                System.err.print(ex.toString() + " >> CLOSING DB");
            }
        }
    }

    public void flushStatementOnly() {
        {
            try {
                statement.close();
                
            } catch (SQLException ex) {
                System.err.print(ex.toString() + " >> CLOSING DB");
            }
        }
    }

}

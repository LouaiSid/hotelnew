package hotel.databaseOperation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import I3.DatabaseOperation.DataBaseConnection;

import hotel.classes.UserInfo;

/**
 *
 * @author Faysal Ahmed
 */
public class DatabaseOperation {

    Connection conn = DataBaseConnection.connectTODB();
    PreparedStatement statement = null;
    ResultSet result = null;

    public void insertCustomer(UserInfo user) throws SQLException {
        //try {
            // String insertQuery = "insert into userInfo('name','address','phone','type')"
            //         + " values('"
            //         + user.getName()
            //         + "','" + user.getAddress() + "'"
            //         + ",'" + user.getPhoneNo() + "'"
            //         + ",'" + user.getType() + "'"
            //         + ")";
        //     String insertQuery = "insert into userInfo"
        //             + "('" + "name" + "'," + "'" + "address" + "','" + "phone" + "','" + "type" + "')"
        //             + " values('"
        //             + user.getName()
        //             + "','" + user.getAddress() + "'"
        //             + ",'" + user.getPhoneNo() + "'"
        //             + ",'" + user.getType() + "'"
        //             + ")";

        //     statement = conn.prepareStatement(insertQuery);

        //     statement.execute();

        //     JOptionPane.showMessageDialog(null, "successfully inserted new Customer");

        // } catch (SQLException ex) {
        //     JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "InsertQuery Failed");
        // }} finally {
  //          flushStatmentOnly();  // Assurez-vous que cette méthode est définie ailleurs pour fermer le statement si nécessaire.
//}
        String insertQuery = "INSERT INTO table_name (column1, column2, column3) VALUES (?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(insertQuery)) {
            statement.setString(1, user.getName());  // Remplacer par le bon champ
            statement.setString(2, user.getPassword());  // Remplacer par le bon champ
            statement.setString(3, user.getType());  // Remplacer par le bon champ
        
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            flushStatmentOnly();  // Assurez-vous que cette méthode est définie ailleurs pour fermer le statement si nécessaire.
}

        
        
    }
    public void flushAll()
    {
        {
                        try
                        {
                            statement.close();
                            result.close();
                        }
                        catch(SQLException ex)
                        {System.err.print(ex.toString()+" >> CLOSING DB");}
                    }
    }

    // public void updateCustomer(UserInfo user) {
    //     try {
    //         String updateQuery = "update userInfo set name = '"
    //                 + user.getName() + "',"
    //                 + "address = '" + user.getAddress() + "',"
    //                 + "phone = '" + user.getPhoneNo() + "',"
    //                 + "type = '" + user.getType() + "' where user_id= "
    //                 + user.getCustomerId();

         
    //         statement = conn.prepareStatement(updateQuery);

    //         statement.execute();

    //         JOptionPane.showMessageDialog(null, "successfully updated new Customer");
    //     } catch (SQLException ex) {
    //         JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "Update query Failed");
    //     }

    // }
    public void updateCustomer(UserInfo user) {
        try {
            String updateQuery = "UPDATE userInfo SET name = ?, address = ?, phone = ?, type = ? WHERE user_id = ?";
    
            PreparedStatement statement = conn.prepareStatement(updateQuery);
            
            // Remplacement des paramètres dans la requête
            statement.setString(1, user.getName());  // Sécuriser la valeur du nom
            statement.setString(2, user.getAddress()); // Sécuriser la valeur de l'adresse
            statement.setString(3, user.getPhoneNo()); // Sécuriser la valeur du téléphone
            statement.setString(4, user.getType());  // Sécuriser la valeur du type
            statement.setInt(5, user.getCustomerId());  // Sécuriser l'ID du client
            
            statement.executeUpdate();  // Utilisation de executeUpdate pour les requêtes de mise à jour
    
            JOptionPane.showMessageDialog(null, "Successfully updated customer");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "Update query failed");
        }
    }
    

    // public void deleteCustomer(int userId) throws SQLException {
    //     try {
    //         String deleteQuery = "delete from userInfo where user_id=" + userId;
    //         statement = conn.prepareStatement(deleteQuery);
    //         statement.execute();
    //         JOptionPane.showMessageDialog(null, "Deleted user");
    //     } catch (SQLException ex) {
    //         JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "Delete query Failed");
    //     }
    //     finally
    //     {
    //         flushStatmentOnly();
    //     }

    // }
            public void deleteCustomer(int userId) throws SQLException {
                try {
                    String deleteQuery = "DELETE FROM userInfo WHERE user_id = ?";
                    PreparedStatement statement = conn.prepareStatement(deleteQuery);
                    statement.setInt(1, userId);  // Utilisation sécurisée de userId comme paramètre
                    statement.execute();
                    JOptionPane.showMessageDialog(null, "Deleted user");
                } catch (SQLException e) {
                    // Gestion des erreurs
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                }
            }
            

    public ResultSet getAllCustomer() {
        try {
            String query = "select * from userInfo";
            statement = conn.prepareStatement(query);
            result = statement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning all customer DB Operation");
        }
        finally
        {
            flushAll();
        }

        return result;
    }

  
    /// ************************************************************************  SEARCH AND OTHERS ************************************************
    public ResultSet searchUser(String user) {
        try {
            // String query = "select user_id,name,address from userInfo where name like '%"+user+"%'";
            
            // statement = conn.prepareStatement(query);
            // result = statement.executeQuery();

            String query = "SELECT user_id, name, address FROM userInfo WHERE name LIKE ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, "%" + user + "%");  // Le paramètre 'user' est ajouté en toute sécurité

            ResultSet result = statement.executeQuery();
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from search user function");
        }
        return result;
    }
    
    public ResultSet searchAnUser(int id)
    {
        try {
            // String query = "select * from userInfo where user_id="+id;
            
            // statement = conn.prepareStatement(query);
            // result = statement.executeQuery();

            String query = "SELECT * FROM userInfo WHERE user_id = ?";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);  // id est maintenant un paramètre sécuritaire

            ResultSet result = statement.executeQuery();
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning AN user function");
        }
            
        return result;
    }
    //version ancienne
    // public ResultSet getAvailableRooms(long checkInTime)
    // {
    //    try {
           
    //       String query = "SELECT room_no FROM room LEFT OUTER JOIN booking ON room.room_no = booking.booking_room WHERE booking.booking_room is null or "+checkInTime+"< booking.check_in " +"or booking.check_out <"+checkInTime+" group by room.room_no  order by room_no ";
    //         System.out.println(query);
    //         statement = conn.prepareStatement(query);
    //         result = statement.executeQuery();
    //     } catch (SQLException ex) {
    //         JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning free rooms from getAvailable func.");
    //     }
        
                
    //         return result;
    //     }
    // }
  
    public ResultSet getAvailableRooms(long checkInTime) {
        ResultSet result = null;
        String query = "SELECT room_no FROM room " +
                       "LEFT OUTER JOIN booking ON room.room_no = booking.booking_room " +
                       "WHERE booking.booking_room IS NULL " +
                       "OR ? < booking.check_in " +
                       "OR booking.check_out < ? " +
                       "GROUP BY room.room_no " +
                       "ORDER BY room_no";
    
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setTimestamp(1, new java.sql.Timestamp(checkInTime));
            statement.setTimestamp(2, new java.sql.Timestamp(checkInTime));
    
            System.out.println("Executing: " + statement.toString());
    
            result = statement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning free rooms from getAvailableRooms func.");
        }
    
        return result;
    }
    
          
    public ResultSet getBookingInfo(LocalDate startDate, LocalDate endDate,String roomNo)
    {
        try {
           
            
            // String query = "select * from booking where booking_room = '"+ roomNo+"' AND ("
            //         +"( check_in <= "+startDate +" and ( check_out = 0 or check_out<= "+endDate+") ) or"
            //         +"( check_in >"+startDate+" and check_out< "+endDate+" ) or"
            //         +"( check_in <= "+endDate +" and ( check_out =0 or check_out> "+endDate+") ) )";
                    
            String query = "SELECT * FROM booking WHERE booking_room = ? AND (" +
                            "(check_in <= ? AND (check_out = 0 OR check_out <= ?)) OR " +
                            "(check_in > ? AND check_out < ?) OR " +
                            "(check_in <= ? AND (check_out = 0 OR check_out > ?))" +
                            ")";

            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, roomNo);                     // booking_room = ?
            statement.setDate(2, java.sql.Date.valueOf(startDate)); // check_in <= ?
            statement.setDate(3, java.sql.Date.valueOf(endDate));   // check_out <= ?
            statement.setDate(4, java.sql.Date.valueOf(startDate)); // check_in > ?
            statement.setDate(5, java.sql.Date.valueOf(endDate));   // check_out < ?
            statement.setDate(6, java.sql.Date.valueOf(endDate));   // check_in <= ?
            statement.setDate(7, java.sql.Date.valueOf(endDate));   // check_out > ?

ResultSet result = statement.executeQuery();

                    
            statement = conn.prepareStatement(query);
            result = statement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning booking info between two specific days");
        }
        

        return result;
    }
    
    public int getCustomerId(UserInfo user)
    { int id = -1;
        try {
            // String query = "select user_id from userInfo where name='"+user.getName()+"' and phone ='"+user.getPhoneNo()+"'";
            
            // System.out.println(query +" <<<");
            // statement = conn.prepareStatement(query);
            // result = statement.executeQuery();
            
            // id = result.getInt("user_id");

            String query = "SELECT user_id FROM userInfo WHERE name = ? AND phone = ?";

            statement = conn.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPhoneNo());
                    
            result = statement.executeQuery();
                    
            if (result.next()) {
                id = result.getInt("user_id");
            } else {
                id = -1; // ou une autre valeur par défaut
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning AN user function");
        }
       
        return id;
    }
    
    
    
    private void flushStatmentOnly()
    {
        {
                        try
                        {
                            statement.close();
                        }
                        catch(SQLException ex)
                        {System.err.print(ex.toString()+" >> CLOSING DB");}
                    }
    }
}

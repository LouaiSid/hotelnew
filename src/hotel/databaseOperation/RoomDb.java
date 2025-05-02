package hotel.databaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import hotel.classes.Room;
import hotel.classes.RoomFare;

/**
 *
 * @author Faysal Ahmed
 */
public class RoomDb {
    Connection conn = DataBaseConnection.connectTODB();
    PreparedStatement statement = null;
    ResultSet result = null;

     public void insertRoom(Room room) {
        // try {
        //     String insertQuery = "insert into room('room_no','bed_number','tv','wifi','gizer','phone','room_class','meal_id')"
        //             + " values('"
        //             + room.getRoomNo()
        //             + "'," + room.getBedNumber() + ""
        //             + ",'" + room.isHasTV() + "'"
        //             + ",'" + room.isHasWIFI() + "'"
        //             + ",'" + room.isHasGizer() + "'"
        //             + ",'" + room.isHasPhone() + "'"
        //             + ",'" + room.getRoomClass().getRoomType() + "'"
        //             + ")";

        //     System.out.println(">>>>>>>>>> "+ room.getRoomClass().getRoomType());
        //     statement = conn.prepareStatement(insertQuery);
        String insertQuery = "INSERT INTO room (column1, column2, column3) VALUES (?, ?, ?)";

            try (PreparedStatement statement = conn.prepareStatement(insertQuery)) {
                statement.setString(1, room.getRoomNo());  // Replace with the actual value for the first column
                statement.setString(2, room.getRoomClass().getRoomType());  // Replace with the actual value for the second column
                statement.setInt(3, room.getBedNumber());     // Replace with the actual value for the third column
            
                System.out.println("Executing: " + statement.toString());
            
                statement.executeUpdate();
            
            

            JOptionPane.showMessageDialog(null, "successfully inserted a new Room ");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "InsertQuery of Room Class Failed");
        }
        finally
        {
            flushStatmentOnly();
        }
    }

    public ResultSet getRooms() {
        try {
            String query = "select * from room";
            statement = conn.prepareStatement(query);
            result = statement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning all Room DB Operation");
        }
        
        return result;
    }
    
    public int getNoOfRooms()
    {
        int rooms = -1;
        try {
            String query = "select count(room_no)  as noRoom from room";
            statement = conn.prepareStatement(query);
            result = statement.executeQuery();
            while(result.next())
            {
                rooms = result.getInt("noRoom");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming count Room DB Operation");
        }
        
        return rooms;
    }
    
    public ResultSet getAllRoomNames()
    {
         try {
            String query = "select room_no from room";
            statement = conn.prepareStatement(query);
            result = statement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning all Room_No  ROOM DB Operation");
        }
        
        return result;
    }

    public void deleteRoom(int roomId) {

        // try {
        //     String deleteQuery = "delete from room where room_id=" + roomId;
        //     statement = conn.prepareStatement(deleteQuery);
        //     statement.execute();
        //     JOptionPane.showMessageDialog(null, "Deleted room");
        // } catch (SQLException ex) {
        //     JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "Delete query room Failed");
        // }
        String deleteQuery = "DELETE FROM room WHERE room_id = ?";

        try (PreparedStatement statement = conn.prepareStatement(deleteQuery)) {
            statement.setInt(1, roomId); // Liaison sécurisée du roomId
        
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted room");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally
        {
            flushStatmentOnly();
        }
    }
    
    public void updateRoom(Room room)
    {
         //try {
            // String updateQuery ="update room set room_no = '"
            //         +room.getRoomNo()+"', bed_number="
            //         +room.getBedNumber()+", tv = '"
            //         +boolToString(room.isHasTV())+"', wifi = '"
            //         +boolToString(room.isHasWIFI())+"',gizer = '"
            //         +boolToString(room.isHasGizer())+"', phone = '"
            //         +boolToString(room.isHasPhone())+"', room_class= '"
            //         +room.getRoomClass().getRoomType()+"', meal_id = "
            //         ;
                    try {
                    String updateQuery = "UPDATE room SET column1 = ?, column2 = ? WHERE id = ?";

                    PreparedStatement statement = conn.prepareStatement(updateQuery);
                    statement.setString(1, room.getRoomNo()); // Replace with the actual value for column1
                    statement.setDouble(2, room.getBedNumber()); // Replace with the actual value for the second column
                    statement.setInt(3, room.getRoomId()); // Use the provided id parameter
                
                    System.out.println("Executing: " + statement.toString());
                
                    statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "successfully updated a room");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "Update query Failed");
        }
         finally
         {
             flushStatmentOnly();
         }

    }

    public String boolToString(boolean value) {
        return value ? "true" : "false";
    }
    
    public void insertRoomType(RoomFare roomType) {
        // try {
        //     String insertRoomTypeQuery = "insert into roomType values('" + roomType.getRoomType() + "'," + roomType.getPricePerDay() + ")";

        //     System.out.println(">>>>>>>>>> " + insertRoomTypeQuery);

        //     statement = conn.prepareStatement(insertRoomTypeQuery);

        //     statement.execute();
        try {
            String insertRoomTypeQuery = "INSERT INTO roomType (type, price) VALUES (?, ?)";
        
            statement = conn.prepareStatement(insertRoomTypeQuery);
            statement.setString(1, roomType.getRoomType());    // Premier "?" : le type
            statement.setDouble(2, roomType.getPricePerDay()); // Deuxième "?" : le prix
        
            System.out.println(">>>>>>>>>> " + statement.toString());
        
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "successfully inserted a new Room Type");

        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "InsertQuery Failed");
        }
        finally
        {
            flushStatmentOnly();
        }
    }

    public ResultSet getRoomType() {
        try {
            String query = "select * from roomType";
            statement = conn.prepareStatement(query);
            result = statement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning all Room Type DB Operation");
        }

        return result;
    }

    public void updateRoomType(RoomFare roomType) {
        // try {
        //     String updateRoomTypeQuery = "update roomType set price= " + roomType.getPricePerDay() + " where type='" + roomType.getRoomType() + "'";

        //     statement = conn.prepareStatement(updateRoomTypeQuery);

        //     statement.execute();
        try {
            String updateRoomTypeQuery = "UPDATE roomType SET price = ? WHERE type = ?";
        
            statement = conn.prepareStatement(updateRoomTypeQuery);
            statement.setDouble(1, roomType.getPricePerDay()); // Remplace ?1 par le prix
            statement.setString(2, roomType.getRoomType());    // Remplace ?2 par le type de chambre
        
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "successfully updated a  Room Type");

        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "UpdateQuery Failed");
        }
        finally
        {
            flushStatmentOnly();
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

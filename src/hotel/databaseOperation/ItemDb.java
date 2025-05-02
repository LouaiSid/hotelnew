/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.databaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import hotel.classes.Item;

/**
 *
 * @author Faysal Ahmed
 */
public class ItemDb {
    Connection conn = DataBaseConnection.connectTODB();
    PreparedStatement statement = null;
    ResultSet result = null;
    
     public void insertItem(Item item) {
        try {
            // String insertItem = "insert into item('name','description','price') values('" + item.getItemName() + "','" + item.getDescription() + "'," + item.getPrice() + ")";

            // statement = conn.prepareStatement(insertItem);

            // statement.execute();

            // JOptionPane.showMessageDialog(null, "successfully inserted a new insertItem");

            String insertItem = "INSERT INTO item(name, description, price) VALUES (?, ?, ?)";

            statement = conn.prepareStatement(insertItem);
            statement.setString(1, item.getItemName());
            statement.setString(2, item.getDescription());
            statement.setDouble(3, item.getPrice());
                    
            statement.executeUpdate(); // utilisez executeUpdate pour les INSERT


        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "InsertQuery of insertItem Failed");
        }
        finally
        {
            flushStatmentOnly();
        }
    }

    public void updateItem(Item item) {
        try {
            // String updateFood = "update food set name= '" + item.getItemName() + "', price= " + item.getPrice() + "description = '" + item.getDescription() + "' where item_id = " + item.getItemId();

            // // System.out.println(">>>>>>>>>> "+ insertRoomTypeQuery);
            // statement = conn.prepareStatement(updateFood);

            // statement.execute();

            // JOptionPane.showMessageDialog(null, "successfully updateitem ");

            String updateFood = "UPDATE food SET name = ?, price = ?, description = ? WHERE item_id = ?";

            statement = conn.prepareStatement(updateFood);
            statement.setString(1, item.getItemName());
            statement.setDouble(2, item.getPrice());
            statement.setString(3, item.getDescription());
            statement.setInt(4, item.getItemId());

            statement.executeUpdate(); // utilisez executeUpdate pour UPDATE
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "Update Item failed");
        }
        finally
        {
            flushStatmentOnly();
        }
    }

    public ResultSet getItems() {
        try {
            String query = "select * from item";
            statement = conn.prepareStatement(query);
            result = statement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning all item DB Operation");
        }

        return result;
    }

    public void deleteItem(int itemId) {
        try {
            // String deleteQuery = "delete from item where item_id=" + itemId;
            // statement = conn.prepareStatement(deleteQuery);
            // statement.execute();
            // JOptionPane.showMessageDialog(null, "Deleted item");

            String deleteQuery = "DELETE FROM item WHERE item_id = ?";
            statement = conn.prepareStatement(deleteQuery);
            statement.setInt(1, itemId);  // protège contre l'injection SQL
            statement.executeUpdate();    // utilise executeUpdate pour DELETE
            JOptionPane.showMessageDialog(null, "Deleted item");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "Delete query Item Failed");
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

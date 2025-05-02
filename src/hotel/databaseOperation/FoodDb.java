package hotel.databaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import hotel.classes.Food;

/**
 *
 * @author Faysal Ahmed
 */
public class FoodDb {
    
    Connection conn = DataBaseConnection.connectTODB();
    PreparedStatement statement = null;
    ResultSet result = null;
    
     public void insertFood(Food food) {
        try {
            // String insertFood = "insert into food('name','price') values('" + food.getName() + "'," + food.getPrice() + ")";

            // statement = conn.prepareStatement(insertFood);

            // statement.execute();

            // JOptionPane.showMessageDialog(null, "successfully inserted a new Food Type");

            String insertFood = "INSERT INTO food(name, price) VALUES (?, ?)";

            statement = conn.prepareStatement(insertFood);
            statement.setString(1, food.getName());
            statement.setDouble(2, food.getPrice());
                    
            statement.executeUpdate(); // Recommandé pour les INSERT

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "InsertQuery of Food Failed");
        }
        finally
        {
            flushStatmentOnly();
        }
    }

    public ResultSet getFoods() {
        try {
            String query = "select * from food";
            statement = conn.prepareStatement(query);
            result = statement.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n error coming from returning all food DB Operation");
        }
        

        return result;
    }

    public void updateFood(Food food) {
        try {
            // String updateFood = "update food set name= '" + food.getName() + "', price= " + food.getPrice() + " where food_id = " + food.getFoodId();

            // statement = conn.prepareStatement(updateFood);

            // statement.execute();

            // JOptionPane.showMessageDialog(null, "successfully updateFood ");

            String updateFood = "UPDATE food SET name = ?, price = ? WHERE food_id = ?";

            statement = conn.prepareStatement(updateFood);
            statement.setString(1, food.getName());
            statement.setDouble(2, food.getPrice());
            statement.setInt(3, food.getFoodId());

            statement.executeUpdate(); // Utilise executeUpdate pour les UPDATE

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "updateFood of Food Failed");
        }
        finally
        {
            flushStatmentOnly();
        }
    }
    public void deleteFood(int foodId) {
        String deleteQuery = "DELETE FROM food WHERE food_id = ?";
    
        try (PreparedStatement statement = conn.prepareStatement(deleteQuery)) {
            statement.setInt(1, foodId); // Liaison sécurisée du foodId
    
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted food");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally
           {
                 flushStatmentOnly();
         }
    }
    // public void deleteFood(int foodId) {
    //     try {
    //         String deleteQuery = "delete from food where food_id=" + foodId;
    //         statement = conn.prepareStatement(deleteQuery);
    //         statement.execute();
    //         JOptionPane.showMessageDialog(null, "Deleted food");
    //     } catch (SQLException ex) {
    //         JOptionPane.showMessageDialog(null, ex.toString() + "\n" + "Delete query FOod Failed");
    //     }
    //     

    
    
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

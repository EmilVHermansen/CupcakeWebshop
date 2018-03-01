package Connector;

import Constructors.Bottom;
import Constructors.User;
import Constructors.Topping;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emilv
 */
public class DataAccessObject
{

    private final DBConnector conn;

    public DataAccessObject() throws Exception
    {
        this.conn = new DBConnector();
    }

    public User getUser(String name) throws SQLException
    {
        Statement stmt = conn.getConnection().createStatement();
        String sql = "select * from login where email = " + "'" + name + "';";
        User user = null;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
        {
            String username = rs.getString("email");
            String password = rs.getString("password");
            int balance = rs.getInt("balance");
            user = new User(username, password, balance);
        }
        return user;
    }

    public Bottom getBottom(String bottomName) throws SQLException
    {
        Statement stmt = conn.getConnection().createStatement();
        String sql = "select * from bottoms where name = " + "'" + bottomName + "';";
        Bottom bottom = null;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
        {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            bottom = new Bottom(name, price);
        }
        return bottom;
    }

    public ArrayList<Bottom> getBottoms() throws Exception
    {
        Statement stmt = conn.getConnection().createStatement();
        String sql = "select * from bottoms;";
        ArrayList<Bottom> bottoms = new ArrayList<Bottom>();
        Bottom bottom = null;
        ResultSet rs = null;
        try
        {
            rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                bottom = new Bottom(name, price);
                bottoms.add(bottom);

            }
        } catch (SQLException ex)
        {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bottoms;
    }

    public Topping getTopping(String toppingName) throws SQLException
    {
        Statement stmt = conn.getConnection().createStatement();
        String sql = "select * from toppings where name = " + "'" + toppingName + "';";
        Topping topping = null;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
        {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            topping = new Topping(name, price);
        }
        return topping;
    }

    public ArrayList<Topping> getToppings() throws Exception
    {
        Statement stmt = conn.getConnection().createStatement();
        String sql = "select * from toppings;";
        ArrayList<Topping> toppings = new ArrayList<Topping>();
        Topping topping = null;
        ResultSet rs = null;
        try
        {
            rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                topping = new Topping(name, price);
                toppings.add(topping);

            }
        } catch (SQLException ex)
        {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toppings;
    }

    public void newUser(String username, String pw, int balance) throws SQLException
    {
        Statement stmt = conn.getConnection().createStatement();
        String sql = "insert into login (email,password,balance) values ('" + username + "','" + pw + "'," + balance + ");";
        try
        {
            stmt.executeUpdate(sql);
        } catch (SQLException ex)
        {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
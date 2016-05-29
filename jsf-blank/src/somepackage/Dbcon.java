package somepackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbcon {

	Connection con = null;
	public Connection getLocalConnection()
	{
		try
		{
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/auto_homeworkDB","homeworkuser","auto16homework");
		}
		catch (ClassNotFoundException e)
		{
		System.err.println("ClassNotFoundException ingetConnection, cant connect "+ e.getMessage());
		}
		catch(SQLException e)
		{
			System.err.println("SQLException in getConnection, "+e.getMessage());
		}
		return 	con ;
	}
	public 	void setConnectionClose() throws SQLException
	{
	con.close();
	}
}

package somepackage;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface UserManag {

	public String adduser(String name,String surname,String mail,String state,String gender,String usertype,String univ_n)
			throws SQLException, NoSuchAlgorithmException ;
	public String modify(User user) throws SQLException, NoSuchAlgorithmException;
}
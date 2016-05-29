package somepackage;

import java.sql.SQLException;

public interface AuthInt {
	public Userauth finduser  (String username,String passwd,String usertype)throws SQLException;
	
}

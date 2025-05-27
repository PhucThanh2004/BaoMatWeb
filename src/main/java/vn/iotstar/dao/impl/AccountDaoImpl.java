package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vn.iotstar.configs.DBConnectSQL;
import vn.iotstar.dao.IAccountDao;
import vn.iotstar.models.AccountModel;

public class AccountDaoImpl extends DBConnectSQL implements IAccountDao {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;

	@Override
	public AccountModel findByUserName(String email) {
		String sql = "SELECT * from accounts where email = ?";
		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();

			while (rs.next()) {
				AccountModel user = new AccountModel();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setSeller(rs.getBoolean("is_seller"));
				user.setPhone(rs.getString("phone"));
				user.setAvatar(rs.getString("avatar"));
				user.setCover_image(rs.getString("cover_image"));

				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void insert(AccountModel acc) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkExistEmail(String email) {
		boolean duplicate = false;
		String sql = "Select * From accounts where email =? ";
		try { 
			conn = new DBConnectSQL().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs=ps.executeQuery();
			if(rs.next()) {
				duplicate =true;
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
		e.printStackTrace();
		}
		return duplicate;

	}

	@Override
	public boolean checkExistPhone(String phone) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String[] args)
	{
		AccountDaoImpl userDao = new AccountDaoImpl();
		//List<UserModel> list = userDao.findAll();
		
		//for(UserModel user : list)
		//{
			//System.out.println(user);
		//}
		
		System.out.print(userDao.findByUserName("huyen123"));
		userDao.updateAvatar(new AccountModel("thanh@gmail.com", "abc"));
		//userDao.updateAccount(new AccountModel("phucThanh", "0394601129", "thanh@gmail.com"));
		
	}

	@Override
	public void updateAvatar(AccountModel acc) {
		 String sql = "UPDATE accounts SET avatar=? WHERE email=?";

		    try {
		        conn = super.getConnection();
		        ps = conn.prepareStatement(sql);

		        ps.setString(1, acc.getAvatar());
		        ps.setString(2, acc.getEmail());

		        ps.executeUpdate();

		        conn.close();
		        ps.close();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
	}

	public void updateAccount(AccountModel acc) {
	    String sql = "UPDATE accounts SET name=?, phone=? WHERE email=?";

	    try {
	        conn = super.getConnection();
	        ps = conn.prepareStatement(sql);

	        ps.setString(1, acc.getName());
	        ps.setString(2, acc.getPhone());
	        ps.setString(3, acc.getEmail());

	        ps.executeUpdate();

	        conn.close();
	        ps.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void updateCoverImage(AccountModel acc) {
		String sql = "UPDATE accounts SET cover_image=? WHERE email=?";

	    try {
	        conn = super.getConnection();
	        ps = conn.prepareStatement(sql);

	        ps.setString(1, acc.getCover_image());
	        ps.setString(2, acc.getEmail());

	        ps.executeUpdate();

	        conn.close();
	        ps.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void insertregister(AccountModel acc) {
		String sql = "INSERT INTO accounts (name, password, email, phone, avatar, cover_image, code, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			conn = super.getConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, acc.getEmail());
			ps.setString(2, acc.getPassword());
			ps.setString(3, acc.getName());
			ps.setString(4, acc.getPhone());
			ps.setString(5, acc.getAvatar());
			ps.setString(6, acc.getCover_image());
			ps.setString(7, acc.getCode());
			ps.setInt(8, acc.getStatus());

			// Thực thi câu lệnh chèn
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		}		
	}

	@Override
	public void updatestatus(AccountModel user) {
		String sql = "UPDATE [accounts] SET status =? , code =? WHERE email = ?";
		try {
		conn = new DBConnectSQL().getConnection();
		ps = conn.prepareStatement(sql);

		ps.setInt(1, user.getStatus());
		ps.setString(2,user.getCode());
		ps.setString(3, user.getEmail());
		ps.executeUpdate();

		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
				
	}

	@Override
	public void updateCode(String email, String code) throws Exception {
		String sql = "UPDATE accounts SET code = ? WHERE email = ?";
	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, code);
	        ps.setString(2, email);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
				
	}

	@Override
	public boolean updatePassword(String email, String code, String newPassword) throws Exception {
		 String sql = "UPDATE accounts SET password = ? WHERE email = ? AND code = ?";
		    try (Connection conn = getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {
		        ps.setString(1, newPassword);
		        ps.setString(2, email);
		        ps.setString(3, code);

		        int rowsUpdated = ps.executeUpdate();
		        return rowsUpdated > 0; // Return true if at least one row is updated
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false; // Return false if an error occurs
		    }
	}


}

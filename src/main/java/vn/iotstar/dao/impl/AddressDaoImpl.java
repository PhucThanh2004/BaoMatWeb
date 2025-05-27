package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.configs.DBConnectSQL;
import vn.iotstar.dao.IAddressDao;
import vn.iotstar.models.AddressModel;

public class AddressDaoImpl extends DBConnectSQL implements IAddressDao {

    public Connection conn = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;

    @Override
    public List<AddressModel> findAllById(String email) {
        String sql = "SELECT * FROM address where email = ?";
        List<AddressModel> list = new ArrayList<>();

        try {
            conn = super.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            while (rs.next()) {
                AddressModel address = new AddressModel();
                address.setId(rs.getInt("id"));
                address.setEmail(rs.getString("email"));
                address.setProvince(rs.getString("province"));
                address.setDistrict(rs.getString("district"));
                address.setWards(rs.getString("wards"));
                address.setDetail(rs.getString("detail"));
                address.setPhone(rs.getString("phone"));

                list.add(address);
            }

            conn.close();
            ps.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AddressModel findByIdAddress(int id) {
        return null;
    }

    // Test methods
    public static void main(String[] args) {
        AddressDaoImpl addressDao = new AddressDaoImpl();
        addressDao.delete(4);
    }

	@Override
	public void insert(AddressModel address) {
		String sql = "INSERT INTO address (email, province, district, wards, detail, phone) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try {
	        conn = super.getConnection();
	        ps = conn.prepareStatement(sql);

	        // Set the values for the prepared statement from the AddressModel object
	        ps.setString(1, address.getEmail());
	        ps.setString(2, address.getProvince());
	        ps.setString(3, address.getDistrict());
	        ps.setString(4, address.getWards());
	        ps.setString(5, address.getDetail());
	        ps.setString(6, address.getPhone());

	        // Execute the insert query
	        ps.executeUpdate();

	        conn.close();
	        ps.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void delete(int id) {
		
			String sql = "DELETE FROM address WHERE id = ?";
			try {
				conn = super.getConnection();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, id);
				ps.executeUpdate();

				conn.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		
	}
}

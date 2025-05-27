package vn.iotstar.dao;

import java.util.List;

import vn.iotstar.models.AddressModel;

public interface IAddressDao {
	AddressModel findByIdAddress(int id);
	
	List<AddressModel> findAllById(String email);
	void insert(AddressModel address);
	void delete(int id);
}

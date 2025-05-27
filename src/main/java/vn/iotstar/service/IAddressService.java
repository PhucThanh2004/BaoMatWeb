package vn.iotstar.service;

import java.util.List;

import vn.iotstar.models.AddressModel;

public interface IAddressService {
	AddressModel findByIdAddress(int id);

	List<AddressModel> findAllById(String email);
	
	void insert(AddressModel address);
	void delete(int id);

}

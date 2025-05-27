package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.IAddressDao;
import vn.iotstar.dao.impl.AddressDaoImpl;
import vn.iotstar.models.AddressModel;
import vn.iotstar.service.IAddressService;

public class AddressServiceImpl implements IAddressService {

	IAddressDao addDao = new AddressDaoImpl();
	@Override
	public AddressModel findByIdAddress(int id) {
		return  addDao.findByIdAddress(id);
	}


	@Override
	public List<AddressModel> findAllById(String email) {
		return addDao.findAllById(email);
	}


	@Override
	public void insert(AddressModel address) {
		addDao.insert(address);
		
	}


	@Override
	public void delete(int id) {
		addDao.delete(id);
		
	}

}

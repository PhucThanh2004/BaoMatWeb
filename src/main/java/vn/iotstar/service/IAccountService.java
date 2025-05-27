package vn.iotstar.service;

import vn.iotstar.models.AccountModel;

public interface IAccountService {

	AccountModel login(String email, String password);

	AccountModel findByUserName(String email);

	void updateAvatar(AccountModel acc);

	void insert(AccountModel acc);

	void updateAccount(AccountModel acc);

	void updateCoverImage(AccountModel acc);

	boolean checkExistEmail(String email);

	boolean checkExistPhone(String phone);
	
	boolean register(String email, String password, String name, String code, String phone, String avatar,
			String cover_image);

	void updatestatus(AccountModel user);

	void updateCode(String email, String code) throws Exception;

	boolean updatePassword(String email, String code, String newPassword)  throws Exception;

}

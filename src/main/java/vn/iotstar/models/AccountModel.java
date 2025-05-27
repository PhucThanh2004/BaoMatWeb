package vn.iotstar.models;

import java.time.LocalDateTime;

public class AccountModel extends BaseModel {
	private int id;
	private String name;
	private String password;
	private String email;
	private boolean isSeller;
	private String phone;
	private String avatar;
	private String cover_image;
	private String code;
	private int status;

	public AccountModel() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsSeller() {
		return isSeller;
	}

	public void setSeller(boolean seller) {
		isSeller = seller;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCover_image() {
		return cover_image;
	}

	public void setCover_image(String cover_image) {
		this.cover_image = cover_image;
	}

	public AccountModel(String email, String avatar) {
		super();
		this.email = email;
		this.avatar = avatar;
	}

	public AccountModel(String name, String email, String code, boolean isCodeBased) {
		
		this.name = name;
		this.email = email;
		this.code = code;
	}

	public AccountModel(String name, String phone, String email) {
		
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public AccountModel(String name, int id, String password, String email, boolean isSeller) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.email = email;
		this.isSeller = isSeller;
	}

	public AccountModel(String name, String password, String email, String phone, String avatar, String cover_image,
			String code, int status) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.avatar = avatar;
		this.cover_image = cover_image;
		this.code = code;
		this.status = status;
	}

	public AccountModel(int id, String name, String password, String email, boolean isSeller, String phone,
			String avatar, String cover_image, String code) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.isSeller = isSeller;
		this.phone = phone;
		this.avatar = avatar;
		this.cover_image = cover_image;
		this.code = code;
	}

	public AccountModel(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, String name, int id,
			String password, String email, boolean isSeller) {
		super(createdAt, updatedAt, deletedAt);
		this.name = name;
		this.id = id;
		this.password = password;
		this.email = email;
		this.isSeller = isSeller;
	}

}

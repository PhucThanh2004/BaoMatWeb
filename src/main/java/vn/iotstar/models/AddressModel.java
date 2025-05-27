package vn.iotstar.models;

public class AddressModel extends BaseModel {
    private String email;
    private String province;
    private String district;
    private String wards;
    private String detail;
    private int id;
    private String phone;
    
    
   
   
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWards() {
        return wards;
    }

    public void setWards(String wards) {
        this.wards = wards;
    }
    
    

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    // Constructors
    public AddressModel() {
    }
    
    

	public AddressModel(String email, String province, String district, String wards, String detail, String phone) {
		super();
		this.email = email;
		this.province = province;
		this.district = district;
		this.wards = wards;
		this.detail = detail;
		this.phone = phone;
	}

	public AddressModel(String email, String province, String district, String wards, String detail, int id,
			String phone) {
		super();
		this.email = email;
		this.province = province;
		this.district = district;
		this.wards = wards;
		this.detail = detail;
		this.id = id;
		this.phone = phone;
	}

	
	
    

    
}

package vn.iotstar.dao;

import vn.iotstar.models.ShopModel;

public interface IShopDao {
	void insert(ShopModel shop) throws Exception;

	ShopModel findByAccountId(int accountId) throws Exception;

	ShopModel findByShopId(int shopId) throws Exception;

	void updateShopInfo(ShopModel shop) throws Exception;
}

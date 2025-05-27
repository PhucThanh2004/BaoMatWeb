package vn.iotstar.service.impl;

import vn.iotstar.dao.IShopDao;
import vn.iotstar.dao.impl.ShopDaoImpl;
import vn.iotstar.models.ShopModel;
import vn.iotstar.service.IShopService;

public class ShopServiceImpl implements IShopService {
    private IShopDao shopDao;

    public ShopServiceImpl() {
        shopDao = new ShopDaoImpl();
    }
    @Override
    public void registerShop(ShopModel shop) throws Exception {
        shopDao.insert(shop);
    }

    @Override
    public ShopModel findByAccountId(int accountId) throws Exception {
        return shopDao.findByAccountId(accountId);
    }
	@Override
	public ShopModel findByShopId(int shopId) throws Exception {
		return shopDao.findByShopId(shopId);
	}
	@Override
	public void updateShopInfo(ShopModel shop) throws Exception {
		shopDao.updateShopInfo(shop);
		
	}
}

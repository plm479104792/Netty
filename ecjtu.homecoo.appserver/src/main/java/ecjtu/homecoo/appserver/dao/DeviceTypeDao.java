package ecjtu.homecoo.appserver.dao;

import ecjtu.homecoo.appserver.domain.DeviceType;


public interface DeviceTypeDao {

    DeviceType selectByPrimaryKey(String deviceTypeId);

}
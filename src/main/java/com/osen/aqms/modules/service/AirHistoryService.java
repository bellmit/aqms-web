package com.osen.aqms.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.osen.aqms.common.model.AirDataModel;
import com.osen.aqms.common.model.AirRealTimeModel;
import com.osen.aqms.common.model.AqiDataToMapModel;
import com.osen.aqms.modules.entity.data.AirHistory;

import java.util.List;
import java.util.Map;

/**
 * User: PangYi
 * Date: 2019-11-30
 * Time: 10:47
 * Description:
 */
public interface AirHistoryService extends IService<AirHistory> {

    /**
     * 获取指定监控参数因子近12小时的历史数据
     *
     * @param deviceNo 设备号
     * @param sensor   参数因子
     * @return 信息
     */
    List<AirDataModel> getAirDataToSensor(String deviceNo, String sensor);

    /**
     * 根据设备号查询最新的实时数据
     *
     * @param deviceNo 设备号
     * @return 信息
     */
    AirRealTimeModel getAirRealtime(String deviceNo);

    /**
     * 根据区域查询设备列表的空气站实时数据
     *
     * @param params 可指定区域，或默认查询全部
     * @return 信息
     */
    List<AqiDataToMapModel> getAirRealtimeList(Map<String, Object> params);
}

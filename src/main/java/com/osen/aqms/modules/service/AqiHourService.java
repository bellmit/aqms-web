package com.osen.aqms.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.osen.aqms.common.model.AqiDataModel;
import com.osen.aqms.modules.entity.data.AqiHour;

import java.util.List;

/**
 * User: PangYi
 * Date: 2019-11-30
 * Time: 10:47
 * Description:
 */
public interface AqiHourService extends IService<AqiHour> {

    /**
     * 根据设备号获取近24小时AQI值
     *
     * @param deviceNo 设备号
     * @return 信息
     */
    List<AqiDataModel> getAqi24HToDeviceNo(String deviceNo);
}
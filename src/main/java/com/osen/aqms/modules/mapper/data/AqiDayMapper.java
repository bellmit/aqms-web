package com.osen.aqms.modules.mapper.data;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.osen.aqms.common.model.SensorMapperModel;
import com.osen.aqms.modules.entity.data.AqiDay;
import io.lettuce.core.dynamic.annotation.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User: PangYi
 * Date: 2019-11-30
 * Time: 9:02
 * Description:
 */
public interface AqiDayMapper extends BaseMapper<AqiDay> {


    /**
     * @param tableName 表名
     * @param devices   设备号列表
     * @param sensor    参数
     * @param time      时间
     * @return 信息
     */
    List<SensorMapperModel> getSensorModel(@Param("tableName") String tableName, @Param("devices") List<String> devices, @Param("sensor") String sensor, @Param("time") LocalDateTime time);
}

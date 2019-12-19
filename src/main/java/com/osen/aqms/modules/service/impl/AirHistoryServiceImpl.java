package com.osen.aqms.modules.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.osen.aqms.common.config.MybatisPlusConfig;
import com.osen.aqms.common.enums.AirSensor;
import com.osen.aqms.common.exception.type.ControllerException;
import com.osen.aqms.common.model.*;
import com.osen.aqms.common.requestVo.AirQueryVo;
import com.osen.aqms.common.requestVo.AirRankVo;
import com.osen.aqms.common.utils.*;
import com.osen.aqms.modules.entity.data.AirHistory;
import com.osen.aqms.modules.entity.data.AqiHour;
import com.osen.aqms.modules.entity.system.Device;
import com.osen.aqms.modules.mapper.data.AirHistoryMapper;
import com.osen.aqms.modules.service.AirHistoryService;
import com.osen.aqms.modules.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * User: PangYi
 * Date: 2019-11-30
 * Time: 10:52
 * Description:
 */
@Service
public class AirHistoryServiceImpl extends ServiceImpl<AirHistoryMapper, AirHistory> implements AirHistoryService {

    @Autowired
    private RedisOpsUtil redisOpsUtil;

    @Autowired
    private DeviceService deviceService;

    @Override
    public List<AirDataModel> getAirDataToSensor(String deviceNO, String sensor) {
        List<AirDataModel> airDataModels = new ArrayList<>(0);
        // 获取设备信息
        Device device = deviceService.findOneDeviceToNo(deviceNO);
        if (device == null)
            throw new ControllerException("无法查询设备");
        // 查询时间
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.minusHours(12);
        // 基本数据表
        List<String> nameList = TableNameUtil.tableNameList(TableNameUtil.Air_history, endTime, startTime);
        if (nameList.size() <= 0)
            return airDataModels;
        // 参数因子
        LambdaQueryWrapper<AirHistory> query = Wrappers.<AirHistory>lambdaQuery();
        if (AirSensor.PM25.getName().equals(sensor)) {
            for (String name : nameList) {
                MybatisPlusConfig.TableName.set(name);
                query.select(AirHistory::getDateTime, AirHistory::getPm25).eq(AirHistory::getDeviceNo, deviceNO).between(AirHistory::getDateTime, endTime, startTime);
                List<AirHistory> histories = super.list(query);
                if (histories != null) {
                    for (AirHistory history : histories) {
                        AirDataModel airDataModel = new AirDataModel();
                        airDataModel.setData(history.getPm25());
                        airDataModel.setDateTime(history.getDateTime());
                        airDataModels.add(airDataModel);
                    }
                }
            }
        }
        if (AirSensor.PM10.getName().equals(sensor)) {
            for (String name : nameList) {
                MybatisPlusConfig.TableName.set(name);
                query.select(AirHistory::getDateTime, AirHistory::getPm10).eq(AirHistory::getDeviceNo, deviceNO).between(AirHistory::getDateTime, endTime, startTime);
                List<AirHistory> histories = super.list(query);
                if (histories != null) {
                    for (AirHistory history : histories) {
                        AirDataModel airDataModel = new AirDataModel();
                        airDataModel.setData(history.getPm10());
                        airDataModel.setDateTime(history.getDateTime());
                        airDataModels.add(airDataModel);
                    }
                }
            }
        }
        if (AirSensor.SO2.getName().equals(sensor)) {
            for (String name : nameList) {
                MybatisPlusConfig.TableName.set(name);
                query.select(AirHistory::getDateTime, AirHistory::getSo2).eq(AirHistory::getDeviceNo, deviceNO).between(AirHistory::getDateTime, endTime, startTime);
                List<AirHistory> histories = super.list(query);
                if (histories != null) {
                    for (AirHistory history : histories) {
                        AirDataModel airDataModel = new AirDataModel();
                        airDataModel.setData(history.getSo2());
                        airDataModel.setDateTime(history.getDateTime());
                        airDataModels.add(airDataModel);
                    }
                }
            }
        }
        if (AirSensor.NO2.getName().equals(sensor)) {
            for (String name : nameList) {
                MybatisPlusConfig.TableName.set(name);
                query.select(AirHistory::getDateTime, AirHistory::getNo2).eq(AirHistory::getDeviceNo, deviceNO).between(AirHistory::getDateTime, endTime, startTime);
                List<AirHistory> histories = super.list(query);
                if (histories != null) {
                    for (AirHistory history : histories) {
                        AirDataModel airDataModel = new AirDataModel();
                        airDataModel.setData(history.getNo2());
                        airDataModel.setDateTime(history.getDateTime());
                        airDataModels.add(airDataModel);
                    }
                }
            }
        }
        if (AirSensor.CO.getName().equals(sensor)) {
            for (String name : nameList) {
                MybatisPlusConfig.TableName.set(name);
                query.select(AirHistory::getDateTime, AirHistory::getCo).eq(AirHistory::getDeviceNo, deviceNO).between(AirHistory::getDateTime, endTime, startTime);
                List<AirHistory> histories = super.list(query);
                if (histories != null) {
                    for (AirHistory history : histories) {
                        AirDataModel airDataModel = new AirDataModel();
                        airDataModel.setData(history.getCo());
                        airDataModel.setDateTime(history.getDateTime());
                        airDataModels.add(airDataModel);
                    }
                }
            }
        }
        if (AirSensor.O3.getName().equals(sensor)) {
            for (String name : nameList) {
                MybatisPlusConfig.TableName.set(name);
                query.select(AirHistory::getDateTime, AirHistory::getO3).eq(AirHistory::getDeviceNo, deviceNO).between(AirHistory::getDateTime, endTime, startTime);
                List<AirHistory> histories = super.list(query);
                if (histories != null) {
                    for (AirHistory history : histories) {
                        AirDataModel airDataModel = new AirDataModel();
                        airDataModel.setData(history.getO3());
                        airDataModel.setDateTime(history.getDateTime());
                        airDataModels.add(airDataModel);
                    }
                }
            }
        }
        if (AirSensor.VOC.getName().equals(sensor)) {
            for (String name : nameList) {
                MybatisPlusConfig.TableName.set(name);
                query.select(AirHistory::getDateTime, AirHistory::getVoc).eq(AirHistory::getDeviceNo, deviceNO).between(AirHistory::getDateTime, endTime, startTime);
                List<AirHistory> histories = super.list(query);
                if (histories != null) {
                    for (AirHistory history : histories) {
                        AirDataModel airDataModel = new AirDataModel();
                        airDataModel.setData(history.getVoc());
                        airDataModel.setDateTime(history.getDateTime());
                        airDataModels.add(airDataModel);
                    }
                }
            }
        }
        return airDataModels;
    }

    @Override
    public AirRealTimeModel getAirRealtime(String deviceNo) {
        AirRealTimeModel realTimeModel = new AirRealTimeModel();
        // 获取设备信息
        Device device = deviceService.findOneDeviceToNo(deviceNo);
        if (device == null)
            throw new ControllerException("无法查询设备");
        realTimeModel.setDevice(device);

        // 获取实时数据
        AqiRealtimeModel aqiRealtimeModel = new AqiRealtimeModel();
        String dataJson = redisOpsUtil.getToMap(TableNameUtil.Air_Realtime, deviceNo);
        if (!StrUtil.isEmpty(dataJson))
            aqiRealtimeModel = JSON.parseObject(dataJson, AqiRealtimeModel.class);
        realTimeModel.setAqiRealtimeModel(aqiRealtimeModel);

        // 默认获取近12小时的PM2.5参数历史曲线
        List<AirDataModel> airDataToSensor = this.getAirDataToSensor(deviceNo, AirSensor.PM25.getName());
        realTimeModel.setDataModels(airDataToSensor);

        return realTimeModel;
    }

    @Override
    public List<AqiDataToMapModel> getAirRealtimeList(Map<String, Object> params) {
        List<AqiDataToMapModel> aqiDataToMapModels = new ArrayList<>(0);
        List<Device> deviceList;
        if (params == null) {
            // 查询全部设备列表数据
            String username = SecurityUtil.getUsername();
            deviceList = deviceService.findDeviceAllToUsername(username);
        } else {
            // 查询
            String address = (String) params.get("address");
            String level = (String) params.get("level");
            deviceList = deviceService.findDeviceGroupByAddress(address, level);
        }
        // 数据获取
        this.aqiDataWrapper(deviceList, aqiDataToMapModels);
        return aqiDataToMapModels;
    }

    @Override
    public List<AqiDataToMapModel> getAirRankToRealtime(AirRankVo airRankVo) {
        List<AqiDataToMapModel> aqiDataToMapModels = new ArrayList<>(0);
        List<Device> deviceList;
        if (airRankVo == null) {
            // 默认查询全部
            String username = SecurityUtil.getUsername();
            deviceList = deviceService.findDeviceAllToUsername(username);
        } else {
            deviceList = deviceService.findDeviceGroupByAddress(airRankVo.getAddress(), airRankVo.getLevel());
        }
        // 数据获取
        this.aqiDataWrapper(deviceList, aqiDataToMapModels);
        // AQI升序排名
        aqiDataToMapModels = aqiDataToMapModels.stream().sorted(Comparator.comparing(AqiDataToMapModel::getAqi)).collect(Collectors.toList());
        return aqiDataToMapModels;
    }

    /**
     * 数据封装
     *
     * @param deviceList         设备列表
     * @param aqiDataToMapModels 参数
     */
    private void aqiDataWrapper(List<Device> deviceList, List<AqiDataToMapModel> aqiDataToMapModels) {
        for (Device device : deviceList) {
            AqiDataToMapModel aqiDataToMapModel = new AqiDataToMapModel();
            aqiDataToMapModel.setDeviceName(device.getDeviceName());
            aqiDataToMapModel.setDeviceNo(device.getDeviceNo());
            String ade = (StrUtil.isNotEmpty(device.getProvince()) ? device.getProvince() : "") + (StrUtil.isNotEmpty(device.getCity()) ? device.getCity() : "") + (StrUtil.isNotEmpty(device.getArea()) ? device.getArea() : "");
            aqiDataToMapModel.setAddress(ade);
            aqiDataToMapModel.setInstallAddress((StrUtil.isNotEmpty(device.getAddress()) ? device.getAddress() : ""));
            aqiDataToMapModel.setLive(device.getLive() == ConstUtil.OPEN_STATUS ? "在线" : "离线");
            aqiDataToMapModel.setLongitude(device.getLongitude());
            aqiDataToMapModel.setLatitude(device.getLatitude());
            // 获取实时数据
            String dataJson = redisOpsUtil.getToMap(TableNameUtil.Air_Realtime, device.getDeviceNo());
            if (dataJson != null) {
                AqiRealtimeModel aqiRealtimeModel = JSON.parseObject(dataJson, AqiRealtimeModel.class);
                BeanUtil.copyProperties(aqiRealtimeModel, aqiDataToMapModel);
            }
            aqiDataToMapModels.add(aqiDataToMapModel);
        }
    }

    @Override
    public List<AqiDataToMapModel> getAirRankToHistory(AirRankVo airRankVo) {
        List<AqiDataToMapModel> aqiDataToMapModels = new ArrayList<>(0);
        List<Device> deviceList;
        if (airRankVo.getAddress() == null && airRankVo.getLevel() == null) {
            // 获取全部设备
            deviceList = deviceService.findDeviceAllToUsername(SecurityUtil.getUsername());
        } else {
            deviceList = deviceService.findDeviceGroupByAddress(airRankVo.getAddress(), airRankVo.getLevel());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstUtil.QUERY_DATE);
        LocalDate time = LocalDate.parse(airRankVo.getTime(), formatter);
        // TableName
        int year = time.getYear();
        String month = (time.getMonthValue() < 10) ? "0" + time.getMonthValue() : "" + time.getMonthValue();
        String tableName = TableNameUtil.Air_history + "_" + year + month;
        for (Device device : deviceList) {
            AqiDataToMapModel aqiDataToMapModel = new AqiDataToMapModel();
            aqiDataToMapModel.setDeviceName(device.getDeviceName());
            aqiDataToMapModel.setDeviceNo(device.getDeviceNo());
            String ade = (StrUtil.isNotEmpty(device.getProvince()) ? device.getProvince() : "") + (StrUtil.isNotEmpty(device.getCity()) ? device.getCity() : "") + (StrUtil.isNotEmpty(device.getArea()) ? device.getArea() : "");
            aqiDataToMapModel.setAddress(ade);
            aqiDataToMapModel.setInstallAddress((StrUtil.isNotEmpty(device.getAddress()) ? device.getAddress() : ""));
            aqiDataToMapModel.setLive(device.getLive() == ConstUtil.OPEN_STATUS ? "在线" : "离线");
            aqiDataToMapModel.setLongitude(device.getLongitude());
            aqiDataToMapModel.setLatitude(device.getLatitude());
            // 数据
            LocalDateTime startTime = LocalDateTime.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth(), 0, 0, 0);
            LocalDateTime endTime = LocalDateTime.of(time.getYear(), time.getMonthValue(), time.getDayOfMonth(), 23, 59, 59);
            AirAvgModel avgToDay = baseMapper.getAvgToDay(tableName, device.getDeviceNo(), startTime, endTime);
            if (avgToDay == null) {
                aqiDataToMapModels.add(aqiDataToMapModel);
            } else {
                AqiHour aqiHour = AQIComputedUtil.computedAqiToHour(device.getDeviceNo(), startTime, avgToDay);
                aqiDataToMapModel.setPm25(aqiHour.getPm25());
                aqiDataToMapModel.setPm10(aqiHour.getNo2());
                aqiDataToMapModel.setSo2(aqiHour.getSo2());
                aqiDataToMapModel.setNo2(aqiHour.getNo2());
                aqiDataToMapModel.setCo(aqiHour.getCo());
                aqiDataToMapModel.setO3(aqiHour.getO3());
                aqiDataToMapModel.setVoc(aqiHour.getVoc());
                aqiDataToMapModel.setAqi(aqiHour.getAqi());
                aqiDataToMapModel.setPollute(aqiHour.getPollute());
                aqiDataToMapModel.setLevel(aqiHour.getLevel());
                aqiDataToMapModel.setQuality(aqiHour.getQuality());
                aqiDataToMapModel.setDateTime(aqiHour.getDateTime());
                aqiDataToMapModels.add(aqiDataToMapModel);
            }
        }
        // AQI升序排名
        aqiDataToMapModels = aqiDataToMapModels.stream().sorted(Comparator.comparing(AqiDataToMapModel::getAqi)).collect(Collectors.toList());
        return aqiDataToMapModels;
    }

    @Override
    public List<AirQueryDataModel> getAirRealtimeHistory(AirQueryVo airQueryVo) {
        List<AirQueryDataModel> airQueryDataModels = new ArrayList<>(0);
        // 获取查询数据表
        List<String> tableNameList = TableNameUtil.tableNameList(TableNameUtil.Air_history, airQueryVo.getStartTime(), airQueryVo.getEndTime());
        // 时间格式换
        List<LocalDateTime> dateTimes = DateTimeUtil.queryTimeFormatter(airQueryVo.getStartTime(), airQueryVo.getEndTime());
        // 查询
        List<AirHistory> historyList = new ArrayList<>(0);
        LambdaQueryWrapper<AirHistory> query = Wrappers.<AirHistory>lambdaQuery();
        for (String name : tableNameList) {
            MybatisPlusConfig.TableName.set(name);
            query.select(AirHistory::getDeviceNo, AirHistory::getPm25, AirHistory::getPm10, AirHistory::getSo2, AirHistory::getNo2, AirHistory::getCo, AirHistory::getO3, AirHistory::getVoc, AirHistory::getDateTime);
            query.eq(AirHistory::getDeviceNo, airQueryVo.getDeviceNo()).between(AirHistory::getDateTime, dateTimes.get(0), dateTimes.get(1));
            // 添加
            historyList.addAll(super.list(query));
        }
        for (AirHistory airHistory : historyList) {
            AirQueryDataModel airQueryDataModel = new AirQueryDataModel();
            BeanUtil.copyProperties(airHistory, airQueryDataModel);
            airQueryDataModels.add(airQueryDataModel);
        }
        return airQueryDataModels;
    }
}

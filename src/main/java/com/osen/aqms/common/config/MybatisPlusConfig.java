package com.osen.aqms.common.config;

import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.osen.aqms.common.utils.TableNameUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * User: PangYi
 * Date: 2019-09-18
 * Time: 9:13
 * Description:数据动态分表设计
 */
@Configuration
public class MybatisPlusConfig {

    public static ThreadLocal<String> TableName = new ThreadLocal<>();

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 分页拦截器
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 动态表名SQL解析器
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        // 分表
        Map<String, ITableNameHandler> tableNameHandlerMap = new HashMap<>();
        // 原始表名
        List<String> tableNames = new ArrayList<>(0);

        tableNames.add(TableNameUtil.Air_history);
        tableNames.add(TableNameUtil.Air_minute);
        tableNames.add(TableNameUtil.Air_hour);
        tableNames.add(TableNameUtil.Air_day);

        tableNames.add(TableNameUtil.Aqi_realtime);
        tableNames.add(TableNameUtil.Aqi_hour);
        tableNames.add(TableNameUtil.Aqi_day);

        tableNames.add(TableNameUtil.Air_alarm);

        // 设备分表规则
        for (String tableName : tableNames) {
            tableNameHandlerMap.put(tableName, new ITableNameHandler() {
                @Override
                public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
                    return TableName.get();
                }
            });
        }

        dynamicTableNameParser.setTableNameHandlerMap(tableNameHandlerMap);
        paginationInterceptor.setSqlParserList(Collections.singletonList(dynamicTableNameParser));
        // 开启 count 的 join 优化,只针对 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}

package com.ahao.spring.boot.datasources;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Component
public class DataSourceAOP implements PriorityOrdered {
    private static Logger logger = LoggerFactory.getLogger(DataSourceAOP.class);

    @Before("@annotation(com.ahao.spring.boot.datasources.annotation.MasterDataSource)")
    public void master() {
        logger.trace("当前数据源切换到 Master 数据库");
        DataSourceContextHolder.master();
    }

    @Before("@annotation(com.ahao.spring.boot.datasources.annotation.SlaveDataSource)")
    public void slave() {
        logger.trace("当前数据源切换到 Slave 数据库");
        DataSourceContextHolder.slave();
    }

    @After("(@annotation(com.ahao.spring.boot.datasources.annotation.MasterDataSource)||" +
            "@annotation(com.ahao.spring.boot.datasources.annotation.SlaveDataSource))")
    public void after(JoinPoint point) {
        DataSourceContextHolder.clear();
    }

    @Override
    public int getOrder() {
        return 0; // 保证在 AOP 最外层
    }
}
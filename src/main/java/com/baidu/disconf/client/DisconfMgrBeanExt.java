/*
 * Copyright (c) 2017, Tsoft and/or its affiliates. All rights reserved.
 * FileName: DisconfMgrBeanExt.java
 * Author:   ningyu
 * Date:     2017年1月5日
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.baidu.disconf.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import com.baidu.disconf.client.store.aspect.DisconfAspectJ;
import com.baidu.disconf.client.store.inner.DisconfCenterHostFilesStore;
import com.baidu.disconf.client.utils.StringUtil;

/**
 * <功能描述>
 * @author ningyu
 * @date 2017年1月5日 下午3:30:13
 */
public class DisconfMgrBeanExt implements BeanDefinitionRegistryPostProcessor, PriorityOrdered, ApplicationContextAware {

	public final static String SCAN_SPLIT_TOKEN = ",";

    private ApplicationContext applicationContext;

    private String scanPackage = null;

    public void destroy() {

        DisconfMgrExt.getInstance().close();
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    /**
     * 这个函数无法达到最高优先级，例如PropertyPlaceholderConfigurer
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    /**
     * 第一次扫描<br/>
     * 在Spring内部的Bean定义初始化后执行，这样是最高优先级的
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        // 为了做兼容
        DisconfCenterHostFilesStore.getInstance().addJustHostFileSet(fileList);

        List<String> scanPackList = StringUtil.parseStringToStringList(scanPackage, SCAN_SPLIT_TOKEN);
        // unique
        Set<String> hs = new HashSet<String>();
        hs.addAll(scanPackList);
        scanPackList.clear();
        scanPackList.addAll(hs);

        // 进行扫描
        DisconfMgrExt.getInstance().setApplicationContext(applicationContext);
        DisconfMgrExt.getInstance().firstScan(scanPackList);

        // register java bean
        registerAspect(registry);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * register aspectJ for disconf get request
     *
     * @param registry
     */
    private void registerAspect(BeanDefinitionRegistry registry) {

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DisconfAspectJ.class);
        beanDefinition.setLazyInit(false);
        beanDefinition.setAbstract(false);
        beanDefinition.setAutowireCandidate(true);
        beanDefinition.setScope("singleton");

        registry.registerBeanDefinition("disconfAspectJ", beanDefinition);
    }

    /*
     * 已经废弃了，不推荐使用
     */
    @Deprecated
    private Set<String> fileList = new HashSet<String>();

    @Deprecated
    public Set<String> getFileList() {
        return fileList;
    }

    @Deprecated
    public void setFileList(Set<String> fileList) {
        this.fileList = fileList;
    }
}



/*
 * Copyright (c) 2017, Jiuye SCM and/or its affiliates. All rights reserved.
 * FileName: DisconfMgrBeanSecond.java
 * Author:   ningyu
 * Date:     2017年1月5日
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.baidu.disconf.client;

/**
 * <功能描述>
 * @author ningyu
 * @date 2017年1月5日 下午5:58:12
 */
public class DisconfMgrBeanSecondExt {

	public void init() {

        DisconfMgrExt.getInstance().secondScan();
    }

    public void destroy() {
    	DisconfMgrExt.getInstance().close();
    }
}



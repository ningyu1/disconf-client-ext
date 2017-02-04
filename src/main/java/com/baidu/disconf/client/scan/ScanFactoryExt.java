/*
 * Copyright (c) 2017, Tsoft and/or its affiliates. All rights reserved.
 * FileName: ScanFactoryExt.java
 * Author:   ningyu
 * Date:     2017年1月5日
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.baidu.disconf.client.scan;

import com.baidu.disconf.client.scan.impl.ScanMgrImplExt;
import com.baidu.disconf.client.support.registry.Registry;

/**
 * <功能描述>
 * @author ningyu
 * @date 2017年1月5日 下午3:55:58
 */
public class ScanFactoryExt extends ScanFactory {

	/**
     * @throws Exception
     */
    public static ScanMgr getScanMgr(Registry registry) throws Exception {

        ScanMgr scanMgr = new ScanMgrImplExt(registry);
        return scanMgr;
    }
}



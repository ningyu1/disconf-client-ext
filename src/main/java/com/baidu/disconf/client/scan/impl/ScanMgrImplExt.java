/*
 * Copyright (c) 2017, Tsoft and/or its affiliates. All rights reserved.
 * FileName: ScanMgrImplExt.java
 * Author:   ningyu
 * Date:     2017年1月5日
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.baidu.disconf.client.scan.impl;

import com.baidu.disconf.client.config.DisClientConfig;
import com.baidu.disconf.client.scan.inner.statically.impl.StaticScannerNonAnnotationFileMgrImplExt;
import com.baidu.disconf.client.support.registry.Registry;

/**
 * <功能描述>
 * @author ningyu
 * @date 2017年1月5日 下午3:57:33
 */
public class ScanMgrImplExt extends ScanMgrImpl {

	public ScanMgrImplExt(Registry registry) {
		super(registry);
	}
	
	@Override
    public void reloadableScan(String fileName) throws Exception {

        if (DisClientConfig.getInstance().getIgnoreDisconfKeySet().contains(fileName)) {
            return;
        }

        StaticScannerNonAnnotationFileMgrImplExt.scanData2Store(fileName);
    }

}



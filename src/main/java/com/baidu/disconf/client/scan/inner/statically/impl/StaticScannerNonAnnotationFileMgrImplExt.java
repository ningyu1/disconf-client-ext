/*
 * Copyright (c) 2017, Jiuye SCM and/or its affiliates. All rights reserved.
 * FileName: StaticScannerNonAnnotationFileMgrImplExt.java
 * Author:   ningyu
 * Date:     2017年1月5日
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.baidu.disconf.client.scan.inner.statically.impl;

import com.baidu.disconf.client.DisClientConfigExt;
import com.baidu.disconf.client.common.constants.SupportFileTypeEnum;
import com.baidu.disconf.client.common.model.DisConfCommonModel;
import com.baidu.disconf.client.common.model.DisconfCenterBaseModel;
import com.baidu.disconf.client.common.model.DisconfCenterFile;
import com.baidu.disconf.client.config.DisClientConfig;
import com.baidu.disconf.client.config.DisClientSysConfig;
import com.baidu.disconf.client.store.DisconfStoreProcessorFactory;
import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.baidu.disconf.core.common.path.DisconfWebPathMgr;

/**
 * <功能描述>
 * @author ningyu
 * @date 2017年1月5日 下午4:05:11
 */
public class StaticScannerNonAnnotationFileMgrImplExt extends StaticScannerNonAnnotationFileMgrImpl {
	
	protected static DisConfCommonModel makeDisConfCommonModel(String app, String env, String version) {

        DisConfCommonModel disConfCommonModel = new DisConfCommonModel();

        // app
        if(!app.isEmpty()) {
        	disConfCommonModel.setApp(app);
        } else {
        	disConfCommonModel.setApp(DisClientConfig.getInstance().APP);
        }

        // env
        if (!env.isEmpty()) {
            disConfCommonModel.setEnv(env);
        } else {
            disConfCommonModel.setEnv(DisClientConfig.getInstance().ENV);
        }

        // version
        if (!version.isEmpty()) {
            disConfCommonModel.setVersion(version);
        } else {
            disConfCommonModel.setVersion(DisClientConfig.getInstance().VERSION);
        }

        return disConfCommonModel;
    }

	/**
    *
    */
   public static DisconfCenterBaseModel getDisconfCenterFile(String fileName) {

       DisconfCenterFile disconfCenterFile = new DisconfCenterFile();

       fileName = fileName.trim();

       //
       // file name
       disconfCenterFile.setFileName(fileName);

       // file type
       disconfCenterFile.setSupportFileTypeEnum(SupportFileTypeEnum.getByFileName(fileName));

       //
       // disConfCommonModel
       DisConfCommonModel disConfCommonModel = makeDisConfCommonModel(DisClientConfigExt.GLOBAL_APP, "", DisClientConfigExt.GLOBAL_VERSION);
       disconfCenterFile.setDisConfCommonModel(disConfCommonModel);

       // Remote URL
       String url = DisconfWebPathMgr.getRemoteUrlParameter(DisClientSysConfig.getInstance().CONF_SERVER_STORE_ACTION,
               disConfCommonModel.getApp(),
               disConfCommonModel.getVersion(),
               disConfCommonModel.getEnv(),
               disconfCenterFile.getFileName(),
               DisConfigTypeEnum.FILE);
       disconfCenterFile.setRemoteServerUrl(url);

       return disconfCenterFile;
   }
   
	/**
    *
    */
   public static void scanData2Store(String fileName) {

	   DisconfCenterBaseModel disconfCenterBaseModel = null;
	   if(isGlobalFile(fileName)) {
		   disconfCenterBaseModel =
	               StaticScannerNonAnnotationFileMgrImplExt.getDisconfCenterFile(fileName);
	   } else {
		   disconfCenterBaseModel =
	               StaticScannerNonAnnotationFileMgrImpl.getDisconfCenterFile(fileName);
	   }

       DisconfStoreProcessorFactory.getDisconfStoreFileProcessor().transformScanData(disconfCenterBaseModel);
   }
   
   public static boolean isGlobalFile(String fileName) {
	   fileName = fileName.trim();
	   if(fileName.startsWith("global")) {
		   return true;
	   } else {
		   return false;
	   }
   }
}



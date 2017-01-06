# disconf-client-ext
disconf的扩展，使其支持global共享配置给所有app

disconf-client-ext增加了global的配置共享给所有的app，为什么要有公共共享的配置文件？

因为在app配置中有很多是公共的配置，如果没有global就需要在自己的app中配置这些配置信息，导致app中配置信息过多，公共的配置信息修改需要通知各业务app修改自己app中的配置，没有达到一处修改，各处使用的目标。

增加了global的支持后，app中的配置减少了，避免了一些由于配置导致的错误，也可以通过global的配置去规范统一app的公共配置，让业务开发不关心公共配置的细节，在使用的时候直接使用无需维护。



让自己的app支持global特性的改造步骤：

1. 在pom中增加dependency
<!-- disconf ext -->
<dependency>
    <groupId>com.jiuyescm.framework</groupId>
    <artifactId>disconf-client-ext</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
2. 修改disconf配置

    2.1. 替换class="com.baidu.disconf.client.DisconfMgrBean"为class="com.baidu.disconf.client.DisconfMgrBeanExt"

    2.2. 替换class="com.baidu.disconf.client.DisconfMgrBeanSecond"为class="com.baidu.disconf.client.DisconfMgrBeanSecondExt"

    2.3. 替换class="com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBean"

        为class="com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBeanExt"

    2.4. 修改locations中配置文件，只保留项目自己的配置文件，例如：

    <bean id="disconfNotReloadablePropertiesFactoryBean"

        class="com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBeanExt">

        <property name="locations">

            <list>

                <value>classpath:/jdbc.properties</value>

            </list>

        </property>

    </bean>

3. 关闭global共享配置(默认是开启的)

    <bean id="disconfNotReloadablePropertiesFactoryBean"

        class="com.baidu.disconf.client.addons.properties.ReloadablePropertiesFactoryBeanExt">

        <property name="locations">

            <list>

                <value>classpath:/jdbc.properties</value>

            </list>

        </property>

        <property name="globalShareEnable" value="false" />

    </bean>

4. 业务无需关注global配置，配置后会自动引入配置文件



注意：以后业务app只需要关注项目特定的配置文件，例如：jdbc.properties,公共服务的配置统一走global。

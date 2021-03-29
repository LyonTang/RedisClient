//******************************************************************
//系统名称：Test
//模块名称：测试
//版本信息
//版本:1.0    日期:2018年9月13日    作者:Leon     备注:新建
//******************************************************************

package cn.ox0a.redis;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <b>概述</b>：
 * <blockquote>测试</blockquote>
 * <p/>
 * <b>功能</b>：
 * <blockquote>测试基类，Junit需要Spring Boot环境时实现</blockquote>
 * @author  <a href="mailto:1284676837@qq.com">唐亮</a>
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApp.class)
public abstract class SpBootTest {
}

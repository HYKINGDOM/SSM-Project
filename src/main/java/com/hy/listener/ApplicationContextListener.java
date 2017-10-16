package main.java.com.hy.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.ServiceConfigurationError;

public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger _log = LoggerFactory.getLogger(ApplicationContextListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (null == contextRefreshedEvent.getApplicationContext().getParent()){
            _log.debug(">>>>>>>>>>>>>初始化完毕<<<<<<<<<<");
            Map<String,Object> baseService = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(BaseService.class);
            for (Object service : baseService.values()){
                _log.debug(">>>>>>>>>{}.initMapper()",service.getClass().getName());
                try {
                    Method initMapper = service.getClass().getMethod("initMapper");
                    initMapper.invoke(service);
                } catch (Exception e) {
                    _log.debug("初始化Service的initMapper方法异常",e);
                    e.printStackTrace();
                }
            }
        }
        //系统入口初始化
        Map<String,BaseInerface> baseInerfaceBeans = contextRefreshedEvent.getApplicationContext().getBeansOfType(BaseInterface.class);


    }
}

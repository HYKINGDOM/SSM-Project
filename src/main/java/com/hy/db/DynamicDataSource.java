package main.java.com.hy.db;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{

    private final static Logger _log = LoggerFactory.getLogger(DynamicDataSource.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = getDataSource();
        _log.info("当前操作的数据源：{}",dataSource);
        return dataSource;
    }

    /**
     * 设置数据源
     */
    public static void setdataSource(String dataSource){
        contextHolder.set(dataSource);
    }

    /**
     * 获取数据源
     */
    public static String getDataSource(){
        String dataSource = contextHolder.get();
        //如果没有选择数据源则使用默认数据源
        if(null == dataSource){
            DynamicDataSource.setdataSource(DataSourceEnum.MASTER.getDefault());
        }
        return contextHolder.get();
    }

    /**
     * 清楚数据源
     */
    public static void clearDataSource(){
        contextHolder.remove();
    }
}

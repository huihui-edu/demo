package myboot.myblog.mapper;

import myboot.myblog.domain.BlogConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ConfigurationMapper {
    /**
     * 查询站点信息
     * @return
     */
    List<BlogConfig> findAllConfigs();

    /**
     * 修改站点信息
     * @return
     */
    int updateWebsite(@Param("name") String configName,@Param("value") String configValue,@Param("date") Date date);
}

package myboot.myblog.service;

import myboot.myblog.domain.BlogConfig;

import java.util.Map;

public interface ConfigurationService {
    /**
     * 查询站点信息
     * @return
     */
    Map<String,String> findAllConfigs();

    /**
     * 修改站点信息
     * @param params
     * @return
     */
    int updateWebsite(Map<String, String> params);

    /**
     * 修改底部栏信息
     * @param params
     * @return
     */
    int updateFooter(Map<String, String> params);

    /***
     * 修改个人信息
     * @param params
     * @return
     */
    int updateUserInfo(Map<String, String> params);
}

package myboot.myblog.service.Impl;

import myboot.myblog.mapper.ConfigurationMapper;
import myboot.myblog.domain.BlogConfig;
import myboot.myblog.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationMapper configurationMapper;

    /**
     * 查询站点信息
     * @return
     */
    @Override
    public Map<String, String> findAllConfigs() {
        //存放Config对象的Map
        Map<String, String> configurations = new HashMap();
        List<BlogConfig> configs = configurationMapper.findAllConfigs();
        for (BlogConfig config : configs) {
            //key为ConfigName,value为Config对象
            configurations.put(config.getConfigName(),config.getConfigValue());
        }
        return configurations;
    }

    /**
     * 修改网站信息
     * @param params
     * @return
     */
    @Override
    @Transactional
    public int updateWebsite(Map<String, String> params) {
        String websiteName = params.get("websiteName");
        String websiteDescription = params.get("websiteDescription");
        String websiteLogo = params.get("websiteLogo");
        String websiteIcon = params.get("websiteIcon");
        int sum = 0;
        sum += configurationMapper.updateWebsite("websiteName",websiteName,new Date());
        sum += configurationMapper.updateWebsite("websiteDescription",websiteDescription,new Date());
        sum += configurationMapper.updateWebsite("websiteLogo",websiteLogo,new Date());
        sum += configurationMapper.updateWebsite("websiteIcon",websiteIcon,new Date());
        return sum;
    }

    /**
     * 修改底部栏信息
     * @param params
     * @return
     */
    @Override
    public int updateFooter(Map<String, String> params) {
        String footerAbout = params.get("footerAbout");
        String footerICP = params.get("footerICP");
        String footerCopyRight = params.get("footerCopyRight");
        String footerPoweredBy = params.get("footerPoweredBy");
        String footerPoweredByURL = params.get("footerPoweredByURL");
        int sum = 0;
        sum += configurationMapper.updateWebsite("footerAbout",footerAbout,new Date());
        sum += configurationMapper.updateWebsite("footerICP",footerICP,new Date());
        sum += configurationMapper.updateWebsite("footerCopyRight",footerCopyRight,new Date());
        sum += configurationMapper.updateWebsite("footerPoweredBy",footerPoweredBy,new Date());
        sum += configurationMapper.updateWebsite("footerPoweredByURL",footerPoweredByURL,new Date());
        return sum;
    }

    /**
     * 修改个人信息
     * @param params
     * @return
     */
    @Override
    public int updateUserInfo(Map<String, String> params) {
        String yourAvatar = params.get("yourAvatar");
        String yourName = params.get("yourName");
        String yourEmail = params.get("yourEmail");
        int sum = 0;
        sum += configurationMapper.updateWebsite("yourAvatar",yourAvatar,new Date());
        sum += configurationMapper.updateWebsite("yourName",yourName,new Date());
        sum += configurationMapper.updateWebsite("yourEmail",yourEmail,new Date());
        return sum;
    }
}

package com.mock.gateway.config;

import com.mock.gateway.common.WhiteUrlInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhao
 * @since 2022-09-05 11:18
 */
@Component
@ConfigurationProperties(prefix="white-list")
public class WhiteConfig {

    private List<WhiteUrlInfo> whiteInfo;

    public List<WhiteUrlInfo> getWhiteInfo() {
        return whiteInfo;
    }
    public void setWhiteInfo(List<WhiteUrlInfo> whiteInfo) {
        this.whiteInfo = whiteInfo;
    }

}

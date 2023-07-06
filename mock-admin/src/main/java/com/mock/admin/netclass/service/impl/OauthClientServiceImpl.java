package com.mock.admin.netclass.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mock.admin.netclass.entity.OauthClient;
import com.mock.admin.netclass.mapper.OauthClientMapper;
import com.mock.admin.netclass.service.OauthClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * OAuth2 客户端业务类
 *
 * @author haoxr
 * @date 2020-11-06
 */
@Service
@RequiredArgsConstructor
public class OauthClientServiceImpl extends ServiceImpl<OauthClientMapper, OauthClient> implements OauthClientService {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 清理客户端缓存
     */
    @Override
    public void cleanCache() {
        Set<String> keys = stringRedisTemplate.keys("auth:oauth-client:*");
        if (CollectionUtil.isNotEmpty(keys)) {
            stringRedisTemplate.delete(keys);
        }
    }
}

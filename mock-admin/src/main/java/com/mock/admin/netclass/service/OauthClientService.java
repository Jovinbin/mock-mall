package com.mock.admin.netclass.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mock.admin.netclass.entity.OauthClient;

public interface OauthClientService extends IService<OauthClient> {
    void cleanCache();
}

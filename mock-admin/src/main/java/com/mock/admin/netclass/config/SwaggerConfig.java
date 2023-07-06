package com.mock.admin.netclass.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author zhao
 * @since 2022-06-13 10:51
 * @description: Kni4j配置类
 */
@Slf4j
@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Value("${knife4j.password_token_url}")
    private String passwordTokenUrl;

    @Bean
    public Docket getApi(){
//        //schema
//        List<GrantType> grantTypes = new ArrayList<>();
//        //密码模式
//        ResourceOwnerPasswordCredentialsGrant resourceOwnerPasswordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(passwordTokenUrl);
//        grantTypes.add(resourceOwnerPasswordCredentialsGrant);
//        OAuth oAuth = new OAuthBuilder().name("oauth2")
//                .grantTypes(grantTypes).build();
//        //context
//        //scope方位
//        List<AuthorizationScope> scopes = new ArrayList<>();
//        scopes.add(new AuthorizationScope("read", "read  resources"));
//        scopes.add(new AuthorizationScope("write", "write resources"));
//        scopes.add(new AuthorizationScope("reads", "read all resources"));
//        scopes.add(new AuthorizationScope("writes", "write all resources"));
//
//        SecurityReference securityReference = new SecurityReference("oauth2", scopes.toArray(new AuthorizationScope[]{}));
//        SecurityContext securityContext = new SecurityContext(Lists.newArrayList(securityReference), PathSelectors.ant("/**"));
//        //schemas
//        List<SecurityScheme> securitySchemes = Lists.newArrayList(oAuth);
//        //securityContext
//        List<SecurityContext> securityContexts = Lists.newArrayList(securityContext);
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.basePackage("com.mock.admin.netclass"))
                .paths(PathSelectors.any())
                .build()
//                .securityContexts(securityContexts)
//                .securitySchemes(securitySchemes)
                .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("mock RESTFUL APIs")
                .description("测试接口文档")
                .termsOfServiceUrl("http://127.0.0.1:9000")
                .version("1.0").build();
    }

}

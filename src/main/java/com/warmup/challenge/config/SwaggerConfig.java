package com.warmup.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket getDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.warmup.challenge.controllers"))
                .paths(PathSelectors.any()).build();
    }
    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("WarmUp API")
                .version("1.0")
                .contact(new Contact("Augusto","","aguriona@yahoo.com.ar"))
                .build();
    }

}

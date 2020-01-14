package com.carrify.web.carrifyweb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.carrify.web.carrifyweb"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .tags(
                        new Tag("Cars", "All about cars"),
                        new Tag("Authorization", "All about authorization"),
                        new Tag("Region Zone", "All about region zones"),
                        new Tag("Rents", "All about rents"),
                        new Tag("Reservations", "All about reservations"),
                        new Tag("Driver licence", "All about driver licences")
                )
                .apiInfo(apiInfo());
    }

    @Bean
    public UiConfiguration tryItOutConfig() {
        final String[] methodsWithTryItOutButton = {};
        return UiConfigurationBuilder.builder().supportedSubmitMethods(methodsWithTryItOutButton).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Carrify API")
                .description("Carrify Project API Documentation.")
                .version("0.0.1-BETA")
                .contact(new Contact("Carrify Development", "", "carrifydevelopment@gmail.com"))
                .build();
    }
}
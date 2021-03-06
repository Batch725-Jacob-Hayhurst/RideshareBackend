package com.revature.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Sets up the configuration for Swagger and sets the title, description, contact, and version 
 * for the documentation of endpoints.
 * 
 * @author Judson Higley
 *
 */

@Configuration
@EnableSwagger2
public class Swagger2Config {
	/**
	 * ApiInfo consists of metadata for the swagger page.
	 * 
	 * @return An ApiInfoBuilder which is used to add custom metadata to a swagger page.
	 */
	
	private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("RIDESHARE")
            .description("RideShare REST API")
            .contact(new Contact("Judson Higley", "www.revature.com", "judson.higley@yahoo.com"))
//            Might need license for google api here (corresponding link)
//            .license("Apache 2.0")
//            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("1.0.0")
            .build();
    }
	
    /**
     * api is needed for swagger to know what api it will be working with.
     * 
     * @return A Docket which selects the api, builds it and also adds custom apiInfo.
     */
    
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors
                .basePackage("com.revature.controllers"))
            .paths(PathSelectors.regex("/.*"))
            .build().apiInfo(apiEndPointsInfo());
    }
}

package com.sami.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {

	/*@Bean
	public GroupedOpenApi actuatorApi() {
		return GroupedOpenApi.builder()
				.group("actuators")
				.pathsToMatch("/actuators/**")
				.build();
	}*/
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.components(new Components())
				.info(apiInfo());
	}
	
	@Bean
    public GroupedOpenApi privateApi() {
        return GroupedOpenApi.builder()
                .group("private-apis")
				.pathsToMatch("/**") 
				.packagesToScan("com.sami")
                .pathsToExclude("/actuator/**")
                .build();
    }
	
	private Info apiInfo() {
		return new Info()
				.title("Spring security with JWT API")
				.description("API for Spring security with JWT")
				.version("2.0")
				.contact(apiContact())
				.license(apiLicence());
	}
	
	private Contact apiContact() {
		return new Contact()
				.name("Md Samiul Arafin")
				.email("sami.cse.1112@gmail.com")
				.url("https://github.com/arafinsami");
	}
	
	private License apiLicence() {
		return new License()
				.name("MIT Licence")
				.url("https://opensource.org/licenses/mit-license.php");
	}
}

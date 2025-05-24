package chloe.movietalk.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private final String JWT_SCHEME_NAME = "JWT";
    private final String BEARER_TOKEN_PREFIX = "Bearer";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MovieTalk API Docs")
                        .description("MovieTalk 프로젝트의 API 문서입니다.")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList(JWT_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(JWT_SCHEME_NAME, new SecurityScheme()
                                .name(JWT_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(BEARER_TOKEN_PREFIX)
                                .bearerFormat(JWT_SCHEME_NAME)
                                .in(SecurityScheme.In.HEADER)));
    }
}

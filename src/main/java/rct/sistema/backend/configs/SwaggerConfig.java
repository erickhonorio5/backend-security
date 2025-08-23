package rct.sistema.backend.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    private final Environment env;

    public SwaggerConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public OpenAPI securityOpenAPI() {
        String[] profiles = env.getActiveProfiles();
        String activeProfile = profiles.length > 0 ? profiles[0] : "default";

        Server server = new Server();
        server.setUrl(
                "prod".equalsIgnoreCase(activeProfile)
                        ? "https://api.sistema.com"
                        : "http://localhost:8080"
        );
        server.setDescription(activeProfile.toUpperCase() + " Server");

        return new OpenAPI()
                .servers(List.of(server))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new Info()
                        .title("API de Autenticação e Segurança")
                        .version("1.0")
                        .description("API de autenticação com JWT e refresh token para uso como boilerplate em aplicações profissionais")
                        .contact(new Contact()
                                .name("Admin")
                                .email("admin@sistema.com")
                                .url("https://sistema.com"))
                        .license(new License()
                                .name("Proprietary")
                                .url("https://www.sistema.com/license"))
                );
    }
}

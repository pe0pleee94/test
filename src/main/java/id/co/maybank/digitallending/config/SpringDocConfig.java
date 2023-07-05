package id.co.maybank.digitallending.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author muhammadmufqi - Digital Non Retail Division
 * @version 1.0 This is class for implement logic business from Entry Point (Individual or Corporate)
 * @since 1.0 (Created June. 22, 2023)
 */
@Configuration
public class SpringDocConfig {
	@Bean
	public OpenAPI myOpenAPI() {
		Server devServer = new Server();
		devServer.setUrl("http://localhost:8080");
		devServer.setDescription("Server URL in Development environment");

		Server prodServer = new Server();
		prodServer.setUrl("http://localhost");
		prodServer.setDescription("Server URL in Production environment");

		Contact contact = new Contact();
		contact.setEmail("digitallending@maybank.co.id");
		contact.setName("Digital Lending SME");
		contact.setUrl("https://www.maybank.co.id");

		Info info = new Info().title("Digital Lending SME").version("1.0")
				.description("This API exposes endpoints for the digital sme API.").termsOfService("http://localhost");

		return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
	}
}
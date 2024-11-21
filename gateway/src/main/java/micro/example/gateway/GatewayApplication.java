package micro.example.gateway;

import micro.example.gateway.config.SecurityConfig;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		ApplicationContext context = (ApplicationContext) SpringApplication.run(GatewayApplication.class, args);
		context.addServlet("mvcHandlerMappingIntrospector", HandlerMappingIntrospector.class.getName());
	}

}

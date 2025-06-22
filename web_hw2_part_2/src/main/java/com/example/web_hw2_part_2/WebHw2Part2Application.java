package com.example.web_hw2_part_2;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.web_hw2_part_2", "com.example.web_hw2_part_2.config"})
public class WebHw2Part2Application {
	public static void main(String[] args) {
		SpringApplication.run(WebHw2Part2Application.class, args);
	}
	@Bean
	public ApplicationRunner logRoutes(RequestMappingHandlerMapping mapping) {
		return args -> {
			System.out.println("📌 Registered HTTP Endpoints:");
			mapping.getHandlerMethods().forEach((info, method) -> {
				RequestMappingInfo requestInfo = info;
				var patternsCondition = requestInfo.getPatternsCondition();
				var methodsCondition = requestInfo.getMethodsCondition();

				String pattern = (patternsCondition != null && !patternsCondition.getPatterns().isEmpty())
						? patternsCondition.getPatterns().iterator().next()
						: "[no pattern]";

				String httpMethod = (methodsCondition != null && !methodsCondition.getMethods().isEmpty())
						? methodsCondition.getMethods().iterator().next().name()
						: "[ANY]";

				System.out.printf("🔹 %-7s %s -> %s#%s%n",
						httpMethod,
						pattern,
						method.getBeanType().getSimpleName(),
						method.getMethod().getName());
			});
		};
	}
}
package org.example.dienluc.config;

import org.example.dienluc.entity.Bill;
import org.example.dienluc.service.dto.bill.BillCreateDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public ModelMapper modelMapper (){
        ModelMapper modelMapper = new ModelMapper();

        // PropertyMap cho BillCreateDto -> Bill
        PropertyMap<BillCreateDto, Bill> billMap = new PropertyMap<BillCreateDto, Bill>() {
            protected void configure() {
                skip().setId(null); // Bỏ qua thuộc tính id

            }
        };
        modelMapper.addMappings(billMap);
        return modelMapper;
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*") // Thay thế bằng URL của Next.js nếu khác
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}

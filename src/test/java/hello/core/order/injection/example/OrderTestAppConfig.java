package hello.core.order.injection.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(
        basePackages = {"hello.core.member", "hello.core.discount", "hello.core.order"},
        includeFilters = @Filter(type = FilterType.ANNOTATION, classes = OrderIncludeComponent.class)
)
public class OrderTestAppConfig {

}

package hello.core;

import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
//        basePackages = "hello.core.member",
//        basePackageClasses = MemberService.class,
        excludeFilters = @ComponentScan.Filter(
            type = FilterType.ANNOTATION,
            classes = Configuration.class)
)
public class AutoAppConfig {

}

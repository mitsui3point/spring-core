package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(
//        basePackages = "hello.core.member",
//        basePackageClasses = MemberService.class,
        excludeFilters = @Filter(
                type = FilterType.ANNOTATION,
                classes = Configuration.class)
)
public class AutoAppConfig {
    @Bean(value = "memoryMemberRepository")
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}

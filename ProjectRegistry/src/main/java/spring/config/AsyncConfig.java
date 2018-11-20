package spring.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;


@Configuration
@EnableAsync
@ComponentScans({@ComponentScan("spring.service")})
public class AsyncConfig implements AsyncConfigurer {

	@Override
    public Executor getAsyncExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}



import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
class TaskPoolConfig {

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);   //线程池核心线程大小   线程池维护线程的最少数量
        executor.setMaxPoolSize(20);      //最大线程池的大小   线程池维护线程的最大数量
        executor.setQueueCapacity(200);			//缓存队列
        executor.setKeepAliveSeconds(70);
        executor.setThreadNamePrefix("MicroService-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());   //拒绝策略
		
//        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
		
		
		
		/*   拒绝策略
    如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。

    如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。

    如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maxPoolSize，建新的线程来处理被添加的任务。

    如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maxPoolSize，那么通过handler所指定的策略来处理此任务。也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程 maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。

    当线程池中的线程数量大于corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
    */
		
    }
}
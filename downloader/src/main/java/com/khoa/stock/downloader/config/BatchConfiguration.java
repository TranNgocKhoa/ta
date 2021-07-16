package com.khoa.stock.downloader.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource) throws Exception {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean("extractSecurityListStep")
    public Step extractSecurityListStep(@Qualifier("extractSecurityList") Tasklet extractSecurityList) {
        return stepBuilderFactory.get("extractSecurityListStep")
                .tasklet(extractSecurityList)
                .build();
    }

    @Bean("updateDailyPriceStep")
    public Step updateDailyPriceStep(@Qualifier("updateDailyPrice") Tasklet updateDailyPrice) {
        return stepBuilderFactory.get("updateDailyPriceStep")
                .tasklet(updateDailyPrice)
                .build();
    }

    @Bean
    public Job updatePriceData(Step extractSecurityListStep, Step updateDailyPriceStep) {
        return jobBuilderFactory.get("updatePriceData")
                .start(extractSecurityListStep)
                .next(updateDailyPriceStep)
                .build();
    }

    protected JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();

        jobLauncher.setJobRepository(this.getJobRepository());
        jobLauncher.setTaskExecutor(this.createTaskExecutor());
        jobLauncher.afterPropertiesSet();

        return jobLauncher;
    }

    private TaskExecutor createTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setThreadNamePrefix("MEMO-BATCH-");
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(10);
        executor.initialize();
        executor.setKeepAliveSeconds(600);
        executor.setAllowCoreThreadTimeOut(true);

        return executor;
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        // override to do not set datasource even if a datasource exist.
    }
}

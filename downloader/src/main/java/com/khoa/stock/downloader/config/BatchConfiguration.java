package com.khoa.stock.downloader.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
//    private final PlatformTransactionManager transactionManager;
//    private final JobRepository jobRepository;
//    private final JobLauncher jobLauncher;
//    private final JobExplorer jobExplorer;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, DataSource dataSource) throws Exception {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
//        this.dataSource = dataSource;
//
//        this.transactionManager = new ResourcelessTransactionManager();
//
//        JobRepositoryFactoryBean jobRepositoryFactory = new JobRepositoryFactoryBean();
//        jobRepositoryFactory.setDataSource(dataSource);
//        jobRepositoryFactory.setTransactionManager(transactionManager);
//
//        jobRepositoryFactory.afterPropertiesSet();
//        this.jobRepository = jobRepositoryFactory.getObject();
//
//        JobExplorerFactoryBean jobExplorerFactory = new JobExplorerFactoryBean();
//        jobExplorerFactory.setDataSource(dataSource);
//        jobExplorerFactory.afterPropertiesSet();
//        this.jobExplorer = jobExplorerFactory.getObject();
//
//        this.jobLauncher = createJobLauncher();
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
        // initialize will use a Map based JobRepository (instead of database)
    }
}

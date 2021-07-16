package com.khoa.stock.downloader.tasklet;

import com.khoa.stock.core.model.Security;
import com.khoa.stock.downloader.contant.ValueConstants;
import com.khoa.stock.downloader.service.SecurityService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@StepScope
@Component
public class ExtractSecurityList implements Tasklet {
    private final SecurityService securityService;

    public ExtractSecurityList(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        List<Security> securities = securityService.getSecurities();

        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put(ValueConstants.SECURITY_LIST, securities);

        return RepeatStatus.FINISHED;
    }
}

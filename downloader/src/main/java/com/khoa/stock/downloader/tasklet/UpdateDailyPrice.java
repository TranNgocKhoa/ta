package com.khoa.stock.downloader.tasklet;

import com.khoa.stock.core.model.Security;
import com.khoa.stock.downloader.service.DailyPriceService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@StepScope
@Component
public class UpdateDailyPrice implements Tasklet {
    private final DailyPriceService dailyPriceService;

    @Value("#{jobExecutionContext[valueConstants.SECURITY_LIST]}")
    private List<Security> securityList;

    public UpdateDailyPrice(DailyPriceService dailyPriceService) {
        this.dailyPriceService = dailyPriceService;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        if (CollectionUtils.isNotEmpty(securityList)) {
            securityList.forEach(security -> dailyPriceService.save(security.getTicker()));
        }

        return RepeatStatus.FINISHED;
    }
}

package com.github.donnyk22.project.services.experimental.asyncFuntion;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.donnyk22.project.exceptions.ResourceNotFoundException;
import com.github.donnyk22.project.models.dtos.AsyncJobResult;
import com.github.donnyk22.project.models.enums.JobStatus;
import com.github.donnyk22.project.utils.RedisTokenUtil;

@Service
public class AsyncFuncServiceImpl implements AsyncFuncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncFuncServiceImpl.class);

    private final RedisTokenUtil redisTokenUtil;

    public AsyncFuncServiceImpl(RedisTokenUtil redisTokenUtil) {
        this.redisTokenUtil = redisTokenUtil;
    }

    @Override
    @Async
    public CompletableFuture<String> sendEmailDummy(String email) {
        try {
            logger.info("Sending email to: " + email);
            Thread.sleep(5000);
            logger.info("Email sent to: " + email);
            return CompletableFuture.completedFuture("Email sent successfully");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.failedFuture(
                new RuntimeException("Email sending was interrupted")
            );
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    @Async
    public CompletableFuture<Void> sendEmailDummyWithJobId(String jobId, String email) {
        try {
            setJobStatus(jobId, JobStatus.RUNNING.val());
            logger.info("Sending email to: " + email);
            Thread.sleep(20000);
            logger.info("Email sent to: " + email);
            setJobStatus(jobId, JobStatus.SUCCESS.val());
            return CompletableFuture.completedFuture(null);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            setJobStatus(jobId, JobStatus.FAILED.val());
            return CompletableFuture.failedFuture(
                new RuntimeException("Email sending was interrupted")
            );
        } catch (Exception e) {
            setJobStatus(jobId, JobStatus.FAILED.val());
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public AsyncJobResult getJobStatus(String jobId) {
        String status = redisTokenUtil.get("AsyncJobStatus", jobId);
        if(status == null) {
            throw new ResourceNotFoundException("Job not found: " + jobId);
        }
        return new AsyncJobResult()
            .setJobId(jobId)
            .setStatus(status);
    }

    @Override
    public void setJobStatus(String jobId, String status) {
        redisTokenUtil.store("AsyncJobStatus", jobId, status, 60, TimeUnit.MINUTES);
    }
    
}

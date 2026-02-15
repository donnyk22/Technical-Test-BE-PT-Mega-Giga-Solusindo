package com.github.donnyk22.project.services.experimental.asyncFuntion;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncFuncServiceImpl implements AsyncFuncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncFuncServiceImpl.class);

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
    
}

package com.github.donnyk22.project.services.experimental.asyncFuntion;

import java.util.concurrent.CompletableFuture;

public interface AsyncFuncService {
    CompletableFuture<String> sendEmailDummy(String email);
}

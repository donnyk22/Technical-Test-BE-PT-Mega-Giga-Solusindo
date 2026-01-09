package com.github.donnyk22.project.services.supports;

import java.util.List;
import java.util.Map;

public interface SupportsService {
    String redisCheckConnection();
    Map<String, String> checkUserLoginCredential();
    List<String> getBeanList();
}

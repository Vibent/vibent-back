package com.vibent.vibentback.common.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Executor that has a pool of threads dedicated to sending emails when needed.
 */
@Component
public class MailTaskExecutor extends ThreadPoolTaskExecutor {

    @Autowired
    MailTaskExecutor() {
        super.setCorePoolSize(2);
        super.setMaxPoolSize(8);
        super.setThreadNamePrefix(this.getClass().getSimpleName());
        super.initialize();
    }
}

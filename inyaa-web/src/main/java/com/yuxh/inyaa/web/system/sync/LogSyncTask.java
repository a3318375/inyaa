package com.yuxh.inyaa.web.system.sync;

import com.byteblogs.plumemo.log.domain.vo.AuthUserLogVO;
import com.byteblogs.plumemo.log.service.AuthUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogSyncTask {

    @Autowired
    private AuthUserLogService sysLogServiceImpl;

    @org.springframework.scheduling.annotation.Async(value = "asyncExecutor")
    public void addLog(AuthUserLogVO sysLog) {
        this.sysLogServiceImpl.saveLogs(sysLog);
    }
}

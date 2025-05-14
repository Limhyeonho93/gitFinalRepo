package kr.or.iei.common.scheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SchedulerInitializer implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[✅ SchedulerListener 시작됨]");
        scheduler = Executors.newScheduledThreadPool(2);

        // 1시에 시작하는 딜레이 계산 후 하루 주기로 실행
        long initialDelay = computeInitialDelayFor1AM();
        scheduler.scheduleAtFixedRate(new InsertInvoiceData(), initialDelay, 24 * 60, TimeUnit.MINUTES);

        // 5분마다 실행
        scheduler.scheduleAtFixedRate(new UpdateManageNo(), 0, 5, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
        System.out.println("[🛑 SchedulerListener 종료됨]");
    }

    // 다음 1AM까지 남은 분 계산
    private long computeInitialDelayFor1AM() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next1AM = now.withHour(1).withMinute(0).withSecond(0).withNano(0);
        if (now.isAfter(next1AM)) {
            next1AM = next1AM.plusDays(1);
        }
        return Duration.between(now, next1AM).toMinutes();
    }
}

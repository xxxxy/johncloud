/**
 * <html>
 * <body>
 * <p> All rights reserved.</p>
 * <p> Created on 2020/4/10</p>
 * <p> Created by huangjy</p>
 * </body>
 * </html>
 */
package com.john.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

/**
 * @author huangjy
 * @since v1.0
 */
public class Resilience4jTest {
    @Test
    public void test1() {
        //获取一个CircuitBreakerRegistry实例
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();

        CircuitBreakerConfig customConfig = CircuitBreakerConfig.custom()
                //设置阈值,超过这个阈值,断路器就会打开
                .failureRateThreshold(50)
                //断路器打开的时间,在到达设置时间之后,断路器会进入到half open状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                //设置环形缓冲区大小
                .ringBufferSizeInHalfOpenState(2)
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry of = CircuitBreakerRegistry.of(customConfig);
        CircuitBreaker john = of.circuitBreaker("john");
        CircuitBreaker john2 = of.circuitBreaker("john", customConfig);
        CheckedFunction0<String> supplier = CircuitBreaker.decorateCheckedSupplier(john, () -> "hello resilience4j");
        Try<String> result = Try.of(supplier).map(data -> "hello world");
        System.out.println(result.isSuccess());
        System.out.println(result.get());
    }

    @Test
    public void test2() {
        CircuitBreakerConfig customConfig = CircuitBreakerConfig.custom()
                //设置阈值,超过这个阈值,断路器就会打开
                .failureRateThreshold(50)
                //断路器打开的时间,在到达设置时间之后,断路器会进入到half open状态
                .waitDurationInOpenState(Duration.ofMillis(1000))
                //设置环形缓冲区大小
                .ringBufferSizeInClosedState(2)
                .build();
        CircuitBreakerRegistry of = CircuitBreakerRegistry.of(customConfig);
        CircuitBreaker circuitBreaker = of.circuitBreaker("circuitBreaker");
        //查看断路器状态
        System.out.println(circuitBreaker.getState());
        circuitBreaker.onError(0, new RuntimeException());
        System.out.println(circuitBreaker.getState());
        circuitBreaker.onError(0, new RuntimeException());
        System.out.println(circuitBreaker.getState());
    }

    @Test
    public void test3() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                //阈值刷新的时间
                .limitRefreshPeriod(Duration.ofMillis(1000))
                //阈值刷新的频次
                .limitForPeriod(3)
                //限流后的冷却时间
                .timeoutDuration(Duration.ofMillis(1000))
                .build();

        //创建一个限流器
        RateLimiter rateLimiter = RateLimiter.of("john", config);
        CheckedRunnable checkedRunnable = RateLimiter.decorateCheckedRunnable(rateLimiter, () -> {
            System.out.println(new Date());
        });
        Try.run(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable)
                .andThenTry(checkedRunnable);
    }

    @Test
    public void test4() {
        RetryConfig config = RetryConfig.custom()
                //重试次数
                .maxAttempts(5)
                //重试间隔
                .waitDuration(Duration.ofMillis(500))
                //需要重试的异常
                .retryExceptions(RuntimeException.class)
                .build();
        //创建一个重试器
        Retry retry = Retry.of("john", config);
        Retry.decorateRunnable(retry, new Runnable() {
            int count = 0;

            //开启重试功能之后,run方法执行时,如果抛出异常,则自动触发重试功能.
            @Override
            public void run() {
                if (count++ < 6) {
                    throw new RuntimeException();
                }
            }
        }).run();
    }
}

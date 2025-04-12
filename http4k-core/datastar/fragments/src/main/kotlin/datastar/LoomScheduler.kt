package datastar

import datastar.TaskResult.Complete
import dev.forkhandles.time.executors.SimpleScheduler
import dev.forkhandles.time.executors.SimpleSchedulerService
import java.time.Duration
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.atomic.AtomicReference

fun loomScheduler() = SimpleSchedulerService(Executors.newScheduledThreadPool(0, Thread.ofVirtual().factory()))

fun SimpleScheduler.scheduleUntilComplete(rate: Duration, task: () -> TaskResult) =
    AtomicReference<ScheduledFuture<*>>()
        .also {
            it.set(scheduleWithFixedDelay({
                if (run(task) is Complete) it.get().cancel(false)
            }, Duration.ZERO, rate))
        }.get()!!

sealed interface TaskResult {
    data object Running : TaskResult
    data object Complete : TaskResult
}
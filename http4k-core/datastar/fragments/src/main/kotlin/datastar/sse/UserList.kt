package datastar.sse

import datastar.TaskResult.Running
import datastar.User
import datastar.Users
import datastar.fragments.ListUsers
import datastar.scheduleUntilComplete
import dev.forkhandles.time.executors.SimpleScheduler
import org.http4k.core.Method.GET
import org.http4k.routing.sse
import org.http4k.routing.sse.bind
import org.http4k.sse.sendMergeFragments
import org.http4k.template.DatastarFragmentRenderer
import java.time.Duration.ofSeconds

fun UserList(renderer: DatastarFragmentRenderer, users: Users, executor: SimpleScheduler) = "/users" bind sse(
    GET to sse {
        var memo = listOf<User.Saved>()
        val future = executor.scheduleUntilComplete(ofSeconds(1)) {
            val latest = users.list()
            if (memo != latest) {
                it.sendMergeFragments(renderer(ListUsers(latest)))
                memo = latest
            }
            Running
        }
        it.sendMergeFragments(renderer())

        it.onClose { future.cancel(false) }
    }
)

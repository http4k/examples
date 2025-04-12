package datastar.sse

import datastar.TaskResult.Complete
import datastar.TaskResult.Running
import datastar.scheduleUntilComplete
import dev.forkhandles.time.executors.SimpleScheduler
import org.http4k.core.Method.GET
import org.http4k.format.Moshi
import org.http4k.format.asDatastarSignal
import org.http4k.routing.RoutingSseHandler
import org.http4k.routing.sse
import org.http4k.routing.sse.bind
import org.http4k.sse.Sse
import org.http4k.sse.sendMergeSignals
import java.io.File
import java.time.Duration.ofMillis
import java.util.zip.ZipFile

fun BadApples(scheduler: SimpleScheduler): RoutingSseHandler {
    val animation = loadAnimation(File("06_datastar_demo/src/main/resources/bad-apples.zip"))

    return "/bad_apple/updates" bind sse(
        GET to sse {
            var currentFrameIdx = 0

            val future = scheduler.scheduleUntilComplete(ofMillis(33)) {
                when {
                    currentFrameIdx >= animation.frames.size -> Complete
                    else -> currentFrameIdx = it.sendNextFrame(animation, currentFrameIdx)
                }
                Running
            }

            it.onClose { future.cancel(true) }
        }
    )
}

data class BadAppleStore(val _contents: String, val percentage: Double)

private fun Sse.sendNextFrame(animation: AsciiAnimation, currentFrameIdx: Int): Int {
    val frame = animation.frames[currentFrameIdx]
    val percentage = 100.0 * (currentFrameIdx + 1) / animation.frames.size

    sendMergeSignals(Moshi.asDatastarSignal(BadAppleStore(frame, percentage)))

    return currentFrameIdx + 1
}

class AsciiAnimation(val frames: List<String>)

private fun loadAnimation(zipFile: File): AsciiAnimation {
    val frames = mutableListOf<String>()

    ZipFile(zipFile).use {
        it.entries().asSequence()
            .sortedBy { it.name }
            .forEach { entry ->
                it.getInputStream(entry).use { inputStream ->
                    frames.add(inputStream.bufferedReader().use { it.readText() })
                }
            }
    }

    return AsciiAnimation(frames)
}

package verysecuresystems

import org.http4k.config.Environment

/**
 * Main entry point. Note that this will not run without setting up the correct environmental variables
 * - see RunnableEnvironment for a demo-able version of the server.
 */
fun main() {
    SecurityMcpServer(Environment.ENV).start()
}

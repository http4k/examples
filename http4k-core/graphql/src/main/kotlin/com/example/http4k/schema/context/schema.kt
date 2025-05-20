@file:Suppress("DEPRECATION")

package com.example.http4k.schema.context

import com.expediagroup.graphql.SchemaGeneratorConfig
import com.expediagroup.graphql.TopLevelObject
import com.expediagroup.graphql.toSchema
import graphql.ExecutionInput.Builder
import graphql.GraphQL.newGraphQL
import org.dataloader.BatchLoader
import org.dataloader.DataLoader
import org.dataloader.DataLoaderFactory
import org.dataloader.DataLoaderRegistry
import org.http4k.graphql.GraphQLRequest
import org.http4k.graphql.GraphQLResponse
import org.http4k.graphql.GraphQLWithContextHandler
import java.util.concurrent.CompletableFuture.supplyAsync
import java.util.concurrent.CompletionStage

/**
 * This is our mutable database with user records.
 */
object UserDb {
    private val userDb = mutableListOf(
        User(id = 1, name = "Jim"),
        User(id = 2, name = "Bob"),
        User(id = 3, name = "Sue"),
        User(id = 4, name = "Rita"),
        User(id = 5, name = "Charlie")
    )

    fun search(ids: List<Long>) = userDb.filter { ids.contains(it.id) }
    fun delete(ids: List<Long>) = userDb.removeIf { ids.contains(it.id) }
}

data class User(val id: Long, val name: String)

class UserQueries {
    fun search(params: Params) = UserDb.search(params.ids)
}

class UserMutations {
    fun delete(params: Params) = UserDb.delete(params.ids)
}

data class Params(val ids: List<Long>)

class UserDbHandler : GraphQLWithContextHandler<String> {
    private val graphQL = newGraphQL(
        toSchema(
            SchemaGeneratorConfig(listOf("com.example.http4k.schema.context")),
            listOf(TopLevelObject(UserQueries())),
            listOf(TopLevelObject(UserMutations()))
        )
    ).build()

    private val dataLoaderRegistry = DataLoaderRegistry().apply {
        register(
            "USER_LOADER",
            DataLoaderFactory.newDataLoader { ids ->
                supplyAsync {
                    UserQueries().search(Params(ids))
                }
            }
        )
    }

    override fun invoke(request: GraphQLRequest, context: String) = GraphQLResponse.from(
        graphQL.execute(
            Builder()
                .query(request.query)
                .variables(request.variables)
                .dataLoaderRegistry(dataLoaderRegistry)
                .context(context)
        )
    )
}

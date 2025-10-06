@file:Suppress("DEPRECATION")

package com.example.http4k.schema.simple

import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema
import graphql.ExecutionInput.Builder
import graphql.GraphQL.newGraphQL
import org.dataloader.DataLoaderFactory
import org.dataloader.DataLoaderRegistry
import org.http4k.graphql.GraphQLHandler
import org.http4k.graphql.GraphQLRequest
import org.http4k.graphql.GraphQLResponse
import java.util.concurrent.CompletableFuture.supplyAsync

/**
 * This is our mutable database with book records.
 */
object BookDb {
    private val userDb = mutableListOf(
        Book(id = 1, name = "Farenheit 451"),
        Book(id = 2, name = "1984"),
        Book(id = 3, name = "Brave New World"),
        Book(id = 4, name = "A Clockwork Orange"),
        Book(id = 5, name = "The Handmaid's Tale")
    )

    fun search(ids: List<Long>) = userDb.filter { ids.contains(it.id) }
    fun delete(ids: List<Long>) = userDb.removeIf { ids.contains(it.id) }
}

data class Book(val id: Long, val name: String)

class BookQueries {
    fun search(params: Params) = BookDb.search(params.ids)
}

class BookMutations {
    fun delete(params: Params) = BookDb.delete(params.ids)
}

data class Params(val ids: List<Long>)

class BookDbHandler : GraphQLHandler {
    private val graphQL = newGraphQL(
        toSchema(
            SchemaGeneratorConfig(listOf("com.example.http4k.schema.simple")),
            listOf(TopLevelObject(BookQueries())),
            listOf(TopLevelObject(BookMutations()))
        )
    ).build()

    private val dataLoaderRegistry = DataLoaderRegistry().apply {
        register(
            "BOOK_LOADER",
            DataLoaderFactory.newDataLoader { ids ->
                supplyAsync {
                    BookQueries().search(Params(ids))
                }
            }
        )
    }

    override fun invoke(request: GraphQLRequest) = GraphQLResponse.from(
        graphQL.execute(
            Builder()
                .query(request.query)
                .variables(request.variables)
                .dataLoaderRegistry(dataLoaderRegistry)
        )
    )
}

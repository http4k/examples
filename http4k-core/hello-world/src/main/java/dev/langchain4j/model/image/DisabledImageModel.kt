package dev.langchain4j.model.image

import dev.langchain4j.data.image.Image
import dev.langchain4j.model.ModelDisabledException
import dev.langchain4j.model.output.Response

/**
 * An [ImageModel] which throws a [ModelDisabledException] for all of its methods
 *
 *
 * This could be used in tests, or in libraries that extend this one to conditionally enable or disable functionality.
 *
 */
class DisabledImageModel : ImageModel {
    override fun generate(prompt: String?): Response<Image?>? {
        throw ModelDisabledException("ImageModel is disabled")
    }

    override fun generate(prompt: String?, n: Int): Response<List<Image>> {
        throw ModelDisabledException("ImageModel is disabled")
    }

    override fun edit(image: Image?, prompt: String?): Response<Image> {
        throw ModelDisabledException("ImageModel is disabled")
    }

    override fun edit(image: Image?, mask: Image?, prompt: String?): Response<Image> {
        throw ModelDisabledException("ImageModel is disabled")
    }
}

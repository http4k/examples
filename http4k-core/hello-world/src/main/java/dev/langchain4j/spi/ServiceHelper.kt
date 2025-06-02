package dev.langchain4j.spi

import java.util.ServiceLoader
import java.util.function.Consumer

/**
 * Utility wrapper around `ServiceLoader.load()`.
 */
object ServiceHelper {
    /**
     * Load all the services of a given type.
     *
     * @param clazz the type of service
     * @param <T>   the type of service
     * @return the list of services, empty if none
    </T> */
    fun <T> loadFactories(clazz: Class<T>): Collection<T> {
        return loadFactories(clazz, null)
    }

    /**
     * Load all the services of a given type.
     *
     *
     * Utility mechanism around `ServiceLoader.load()`
     *
     *
     *  * If classloader is `null`, will try `ServiceLoader.load(clazz)`
     *  * If classloader is not `null`, will try `ServiceLoader.load(clazz, classloader)`
     *
     *
     *
     * If the above return nothing, will fall back to `ServiceLoader.load(clazz, $this class loader$)`
     *
     * @param clazz       the type of service
     * @param classLoader the classloader to use, may be null
     * @param <T>         the type of service
     * @return the list of services, empty if none
    </T> */
    fun <T> loadFactories(
        clazz: Class<T>,  /*  */
        classLoader: ClassLoader?
    ): Collection<T> {
        var result: List<T>
        result = if (classLoader != null) {
            loadAll(ServiceLoader.load(clazz, classLoader))
        } else {
            // this is equivalent to:
            // ServiceLoader.load(clazz, TCCL);
            loadAll(ServiceLoader.load(clazz))
        }
        if (result.isEmpty()) {
            // By default, ServiceLoader.load uses the TCCL, this may not be enough in environment dealing with
            // classloaders differently such as OSGi. So we should try to use the classloader having loaded this
            // class. In OSGi it would be the bundle exposing vert.x and so have access to all its classes.
            result = loadAll(ServiceLoader.load(clazz, ServiceHelper::class.java.classLoader))
        }
        return result
    }

    /**
     * Load all the services from a ServiceLoader.
     *
     * @param loader the loader
     * @param <T>    the type of service
     * @return the list of services, empty if none
    </T> */
    private fun <T> loadAll(loader: ServiceLoader<T>): List<T> {
        val list: MutableList<T> = ArrayList()
        loader.iterator().forEachRemaining(Consumer { e: T -> list.add(e) })
        return list
    }
}

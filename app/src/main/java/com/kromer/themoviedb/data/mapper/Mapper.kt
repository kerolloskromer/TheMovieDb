package com.kromer.themoviedb.data.mapper

import com.kromer.themoviedb.data.local.MovieLocal
import com.kromer.themoviedb.data.remote.MovieRemote
import com.kromer.themoviedb.domain.model.Movie

/**
 * Mapper class used to transform [MovieRemote] or [MovieLocal] (in the data layer) to [Movie]
 * in the domain layer and vice versa.
 */
class Mapper {

    /**
     * Transform a [MovieRemote] into an [Movie].
     * @param from  Object to be transformed.
     * @return [Movie]
     */
    fun transformRemoteToModel(from: MovieRemote): Movie =
        Movie(
            from.id,
            from.popularity,
            from.voteAverage,
            from.title,
            from.overview,
            from.releaseDate,
            from.posterPath
        )

    /**
     * Transform a Collection of [MovieRemote] into a List of [Movie].
     * @param from Object Collection to be transformed.
     * @return list of [Movie]
     */
    fun transformRemoteToModel(from: Collection<MovieRemote>): List<Movie> =
        from.map { transformRemoteToModel(it) }


    /**
     * Transform a [MovieLocal] into an [Movie].
     * @param from Object to be transformed.
     * @return [Movie]
     */
    fun transformLocalToModel(from: MovieLocal): Movie =
        Movie(
            from.id,
            from.popularity,
            from.voteAverage,
            from.title,
            from.overview,
            from.releaseDate,
            from.posterPath
        )


    /**
     * Transform a Collection of [MovieLocal] into a List of [Movie].
     * @param from Object Collection to be transformed.
     * @return list of [Movie]
     */
    fun transformLocalToModel(from: Collection<MovieLocal>): List<Movie> =
        from.map { transformLocalToModel(it) }


    /**
     * Transform a [Movie] into an [MovieLocal].
     * @param from Object to be transformed.
     * @return [Movie]
     */
    fun transformModelToLocal(from: Movie): MovieLocal =
        MovieLocal(
            from.id,
            from.popularity,
            from.voteAverage,
            from.title,
            from.overview,
            from.releaseDate,
            from.posterPath
        )


    /**
     * Transform a Collection of [Movie] into a List of [MovieLocal].
     * @param from Object Collection to be transformed.
     * @return list of [MovieLocal]
     */
    fun transformModelToLocal(from: Collection<Movie>): List<MovieLocal> =
        from.map { transformModelToLocal(it) }
}
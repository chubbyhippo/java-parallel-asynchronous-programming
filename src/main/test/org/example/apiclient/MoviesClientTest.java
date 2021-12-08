package org.example.apiclient;

import org.example.util.CommonUtil;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MoviesClientTest {
    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/movies")
            .build();

    MoviesClient moviesClient = new MoviesClient(webClient);

    @RepeatedTest(10)
    void retrieveMovie() {
        CommonUtil.startTimer();
        var movieInfoId = 1L;

        var movie = moviesClient.retrieveMovie(movieInfoId);

        System.out.println("movie : " + movie);
        CommonUtil.timeTaken();
        CommonUtil.stopWatchReset();
        assert movie != null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;
    }

    @RepeatedTest(10)
    void retrieveMovieCompletableFuture() {
        CommonUtil.startTimer();
        var movieInfoId = 1L;

        var movie = moviesClient.retrieveMovieCompletableFuture(movieInfoId).join();

        System.out.println("movie : " + movie);
        CommonUtil.timeTaken();
        CommonUtil.stopWatchReset();
        assert movie != null;
        assertEquals("Batman Begins", movie.getMovieInfo().getName());
        assert movie.getReviewList().size() == 1;
    }

    @RepeatedTest(10)
    void retrieveMovies() {
        CommonUtil.startTimer();
        var movieInfoId = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);

        var movies = moviesClient.retrieveMovies(movieInfoId);

        CommonUtil.timeTaken();
        CommonUtil.stopWatchReset();
        assertNotNull(movies);
        assertEquals(7, movies.size());
    }

    @RepeatedTest(10)
    void retrieveMoviesCompletableFuture() {
        CommonUtil.startTimer();
        var movieInfoId = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L);

        var movies = moviesClient.retrieveMoviesCompletableFuture(movieInfoId);

        CommonUtil.timeTaken();
        CommonUtil.stopWatchReset();
        assertNotNull(movies);
        assertEquals(7, movies.size());
    }
}

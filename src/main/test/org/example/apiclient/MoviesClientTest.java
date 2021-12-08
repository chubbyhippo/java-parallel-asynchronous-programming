package org.example.apiclient;

import org.example.util.CommonUtil;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoviesClientTest {
    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/movies")
            .build();

    MoviesClient moviesClient = new MoviesClient(webClient);

    @RepeatedTest(10)
    void retrieveMovies() {
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
    void retrieveMoviesCompletableFuture() {
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
}

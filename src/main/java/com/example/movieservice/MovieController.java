package com.example.movieservice;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Movie> getMovies() {
        return movieRepository.findWithTailableCursorBy().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(path = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getRandomMovies() {
        return Flux.interval(Duration.ofMillis(1000l)).map(a -> UUID.randomUUID().toString());
    }

    @GetMapping(path = "/create", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Movie> addMovie() {
        return this.movieRepository.save(new Movie(UUID.randomUUID().toString(), "Iron Man"));
    }

}

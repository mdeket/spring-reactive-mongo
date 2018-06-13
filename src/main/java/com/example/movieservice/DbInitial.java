package com.example.movieservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import java.util.UUID;

@Component
@Slf4j
public class DbInitial implements CommandLineRunner {

    private final MovieRepository movieRepository;

    public DbInitial(MovieRepository posts) {
        this.movieRepository = posts;
    }

    @Override
    public void run(String[] args) {
        log.info("Redis initialization");
        Long size = this.movieRepository.count().block();
        if (size == 0) {
            Flux.just("Top Gun", "Transformers", "Pirates of Caribbean")
                    .flatMap(movieTitle -> {
                        Movie movie = new Movie(UUID.randomUUID().toString(), movieTitle);
                        return movieRepository.insert(movie);
                    })
                    .map(Movie::toString)
                    .subscribe(
                        log::info,
                        error -> log.error("Something went wrong!", error),
                        () -> log.info("Done initialization")
                    );
        }
    }

}

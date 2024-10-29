package com.nttdata.reviews.service.impl;

import com.nttdata.reviews.entity.Movie;
import com.nttdata.reviews.entity.Review;
import com.nttdata.reviews.feign.MovieFeignClient;
import com.nttdata.reviews.repository.ReviewRepository;
import com.nttdata.reviews.service.ReviewService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private MovieFeignClient movieFeignClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ReviewRepository reviewRepository;
    @Override
    public List<Review> listarTodos() {
        return reviewRepository.findAll();
    }

    //las anotaciones solo funcionan con conf yml o properties
    @Override

    @CircuitBreaker(name="moviebuscar", fallbackMethod = "metodoAlternativo")
    public Review crear(Review review) {
        //Movie movie = restTemplate.getForObject("http://localhost:8002/movie/buscar/"+review.getMovie().getId(), Movie.class);

        //Movie movie = restTemplate.getForObject("http://movie-service/movie/buscar/"+review.getMovie().getId(), Movie.class);
        //Movie movie = movieFeignClient.buscarPorId(review.getMovie().getId());

        //Circuit Breaker sin usar anotaciones @CircuitBreaker en el metodo crear
        /*
        return circuitBreakerFactory.create("moviebuscar").run(() -> {
            Movie movie =  movieFeignClient.buscarPorId(review.getMovie().getId());
            if(Objects.nonNull(movie)){
                Review reviewCreated = reviewRepository.save(review);
                //movie.setListReview(new ArrayList<>());
                reviewCreated.setMovie(movie);
                return reviewCreated;
            }
            return null;
        }, ex ->{
            return metodoAlternativo(ex);
        });
        */
        Movie movie =  movieFeignClient.buscarPorId(review.getMovie().getId());
        if(Objects.nonNull(movie)){
            Review reviewCreated = reviewRepository.save(review);
            //movie.setListReview(new ArrayList<>());
            reviewCreated.setMovie(movie);
            return reviewCreated;
        }
        return null;

    }

    private Review metodoAlternativo(Throwable ex){
        System.out.println(ex.getMessage());
        Review reviewAlternative = new Review();
        reviewAlternative.setId(0L);
        reviewAlternative.setComment("Comentario por defecto");
        reviewAlternative.setReviewer("Reviewer default");

        return reviewAlternative;
    }

    @Override
    public Review buscarPorId(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }
}

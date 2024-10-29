package com.nttdata.reviews.feign;

import com.nttdata.reviews.entity.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="movie-service")
public interface MovieFeignClient {

    //@GetMapping("movie/buscar/{id}")
    @GetMapping("/buscar/{id}")
    public Movie buscarPorId(@PathVariable(name="id") Long id);
}

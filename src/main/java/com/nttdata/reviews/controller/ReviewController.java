package com.nttdata.reviews.controller;

import com.nttdata.reviews.entity.Review;
import com.nttdata.reviews.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/listar")
    public List<Review> listarTodos(){
        return reviewService.listarTodos();
    }

    @PostMapping("/crear")
    public Review crear(@RequestBody Review review){
        return reviewService.crear(review);
    }

    @GetMapping("/buscar/{id}")
    public Review buscarPorId(@PathVariable(name="id") Long id){
        return reviewService.buscarPorId(id);
    }
}

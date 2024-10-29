package com.nttdata.reviews.service;

import com.nttdata.reviews.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> listarTodos();

    Review crear(Review review);

    Review buscarPorId(Long id);
}

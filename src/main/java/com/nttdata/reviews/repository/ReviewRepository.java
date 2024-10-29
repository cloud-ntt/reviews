package com.nttdata.reviews.repository;


import com.nttdata.reviews.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

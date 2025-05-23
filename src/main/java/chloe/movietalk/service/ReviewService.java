package chloe.movietalk.service;

import chloe.movietalk.dto.request.CreateReviewRequest;
import chloe.movietalk.dto.request.UpdateReviewRequest;
import chloe.movietalk.dto.response.review.ReviewByMovieResponse;
import chloe.movietalk.dto.response.review.ReviewDetailResponse;

import java.util.List;

public interface ReviewService {

    public List<ReviewByMovieResponse> getAllReviewsByMovie(Long movieId);

    public ReviewDetailResponse createReview(CreateReviewRequest request);

    public ReviewDetailResponse updateReview(Long id, UpdateReviewRequest request);

    public void deleteReview(Long id);
}

package chloe.movietalk.repository;

import chloe.movietalk.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);

    Optional<ReviewLike> findByUserIdAndReviewId(Long userId, Long reviewId);
}

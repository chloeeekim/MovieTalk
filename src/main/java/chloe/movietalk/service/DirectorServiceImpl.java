package chloe.movietalk.service;

import chloe.movietalk.domain.Director;
import chloe.movietalk.domain.Movie;
import chloe.movietalk.dto.request.DirectorRequest;
import chloe.movietalk.dto.response.director.DirectorDetailResponse;
import chloe.movietalk.dto.response.director.DirectorInfoResponse;
import chloe.movietalk.exception.director.DirectorNotFoundException;
import chloe.movietalk.exception.movie.MovieNotFoundException;
import chloe.movietalk.repository.DirectorRepository;
import chloe.movietalk.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;

    @Override
    public List<DirectorInfoResponse> getAllDirectors() {
        return directorRepository.findAll().stream()
                .map(DirectorInfoResponse::fromEntity)
                .toList();
    }

    @Override
    public DirectorDetailResponse getDirectorById(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> DirectorNotFoundException.EXCEPTION);
        return DirectorDetailResponse.fromEntity(director);
    }

    @Override
    public List<DirectorInfoResponse> searchDirector(String keyword) {
        return directorRepository.findByNameContaining(keyword).stream()
                .map(DirectorInfoResponse::fromEntity)
                .toList();
    }

    @Override
    public DirectorInfoResponse createDirector(DirectorRequest request) {
        Director save = directorRepository.save(request.toEntity());
        return DirectorInfoResponse.fromEntity(save);
    }

    @Override
    public DirectorInfoResponse updateDirector(Long id, DirectorRequest request) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> DirectorNotFoundException.EXCEPTION);

        director.updateDirector(request.toEntity());
        return DirectorInfoResponse.fromEntity(director);
    }

    @Override
    public void deleteDirector(Long id) {
        directorRepository.findById(id)
                .orElseThrow(() -> DirectorNotFoundException.EXCEPTION);
        directorRepository.deleteById(id);
    }

    @Override
    public DirectorDetailResponse updateDirectorFilmography(Long id, List<Long> filmography) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> DirectorNotFoundException.EXCEPTION);

        director.getFilmography()
                .forEach(Movie::removeDirector);
        director.getFilmography().clear();

        filmography.stream()
                .map(l -> movieRepository.findById(l).orElseThrow(() -> MovieNotFoundException.EXCEPTION))
                .forEach(m -> m.changeDirector(director));

        return DirectorDetailResponse.fromEntity(director);
    }
}

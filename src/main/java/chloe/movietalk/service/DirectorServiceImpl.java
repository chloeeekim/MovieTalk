package chloe.movietalk.service;

import chloe.movietalk.domain.Director;
import chloe.movietalk.dto.request.DirectorRequest;
import chloe.movietalk.dto.response.DirectorResponse;
import chloe.movietalk.dto.response.MovieInfo;
import chloe.movietalk.exception.director.DirectorNotFoundException;
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
    public List<DirectorResponse> getAllDirectors() {
        return directorRepository.findAll().stream()
                .map(director -> DirectorResponse.fromEntity(director, getMovieInfo(director.getId())))
                .toList();
    }

    @Override
    public DirectorResponse getDirectorById(Long id) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> DirectorNotFoundException.EXCEPTION);
        return DirectorResponse.fromEntity(director, getMovieInfo(director.getId()));
    }

    @Override
    public List<DirectorResponse> searchDirector(String keyword) {
        return directorRepository.findByNameContaining(keyword).stream()
                .map(director -> DirectorResponse.fromEntity(director, getMovieInfo(director.getId())))
                .toList();
    }

    @Override
    public DirectorResponse createDirector(DirectorRequest request) {
        Director save = directorRepository.save(request.toEntity());
        return DirectorResponse.fromEntity(save, null);
    }

    @Override
    public DirectorResponse updateDirector(Long id, DirectorRequest request) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> DirectorNotFoundException.EXCEPTION);

        director.updateDirector(request.toEntity());
        return DirectorResponse.fromEntity(director, getMovieInfo(director.getId()));
    }

    @Override
    public void deleteDirector(Long id) {
        directorRepository.deleteById(id);
    }

    private List<MovieInfo> getMovieInfo(Long id) {
        return movieRepository.findByDirectorId(id).stream()
                .map(MovieInfo::fromEntity)
                .toList();
    }
}

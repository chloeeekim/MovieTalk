package chloe.movietalk.service;

import chloe.movietalk.dto.request.DirectorRequest;
import chloe.movietalk.dto.response.director.DirectorDetailResponse;
import chloe.movietalk.dto.response.director.DirectorInfoResponse;

import java.util.List;
import java.util.UUID;

public interface DirectorService {

    public List<DirectorInfoResponse> getAllDirectors();

    public DirectorDetailResponse getDirectorById(UUID id);

    public List<DirectorInfoResponse> searchDirector(String keyword);

    public DirectorInfoResponse createDirector(DirectorRequest request);

    public DirectorInfoResponse updateDirector(UUID id, DirectorRequest request);

    public void deleteDirector(UUID id);

    public DirectorDetailResponse updateDirectorFilmography(UUID id, List<UUID> movieIds);
}

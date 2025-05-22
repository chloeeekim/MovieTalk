package chloe.movietalk.service;

import chloe.movietalk.dto.request.DirectorRequest;
import chloe.movietalk.dto.response.DirectorResponse;

import java.util.List;

public interface DirectorService {

    public List<DirectorResponse> getAllDirectors();

    public DirectorResponse getDirectorById(Long id);

    public List<DirectorResponse> searchDirector(String keyword);

    public DirectorResponse createDirector(DirectorRequest request);

    public DirectorResponse updateDirector(Long id, DirectorRequest request);

    public void deleteDirector(Long id);
}

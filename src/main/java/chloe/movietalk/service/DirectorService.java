package chloe.movietalk.service;

import chloe.movietalk.dto.request.DirectorRequest;
import chloe.movietalk.dto.response.director.DirectorDetailResponse;
import chloe.movietalk.dto.response.director.DirectorInfoResponse;

import java.util.List;

public interface DirectorService {

    public List<DirectorInfoResponse> getAllDirectors();

    public DirectorDetailResponse getDirectorById(Long id);

    public List<DirectorInfoResponse> searchDirector(String keyword);

    public DirectorInfoResponse createDirector(DirectorRequest request);

    public DirectorInfoResponse updateDirector(Long id, DirectorRequest request);

    public void deleteDirector(Long id);
}

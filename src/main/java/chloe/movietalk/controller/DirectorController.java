package chloe.movietalk.controller;

import chloe.movietalk.dto.request.DirectorRequest;
import chloe.movietalk.dto.response.DirectorResponse;
import chloe.movietalk.service.DirectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/directors")
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public List<DirectorResponse> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/{id}")
    public DirectorResponse getDirectorById(@PathVariable Long id) {
        return directorService.getDirectorById(id);
    }

    @GetMapping("/search")
    public List<DirectorResponse> searchDirectors(@RequestParam String keyword) {
        return directorService.searchDirector(keyword);
    }

    @PostMapping
    public ResponseEntity<DirectorResponse> createDirector(@RequestBody @Valid DirectorRequest request) {
        DirectorResponse director = directorService.createDirector(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(director);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorResponse> updateDirector(@PathVariable Long id, @RequestBody @Valid DirectorRequest request) {
        DirectorResponse director = directorService.updateDirector(id, request);
        return ResponseEntity.ok().body(director);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }
}

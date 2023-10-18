package com.betrybe.museumfinder.controller;

import com.betrybe.museumfinder.dto.MuseumCreationDto;
import com.betrybe.museumfinder.dto.MuseumDto;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.service.MuseumServiceInterface;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * MuseumService.
 */
@RestController
@RequestMapping("/museums")
public class MuseumController {
  private final MuseumServiceInterface museumService;

  @Autowired
  public MuseumController(MuseumServiceInterface museumService) {
    this.museumService = museumService;
  }

  /**
   * post.
   */
  @PostMapping
  public ResponseEntity<Museum> createMuseum(@RequestBody MuseumCreationDto museumCreationDto) {
    Museum museum = convertToMuseum(museumCreationDto);

    Museum createdMuseum = museumService.createMuseum(museum);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdMuseum);
  }


  private Museum convertToMuseum(MuseumCreationDto museumCreationDto) {
    Museum museum = new Museum();
    museum.setName(museumCreationDto.name());
    museum.setDescription(museumCreationDto.description());
    museum.setAddress(museumCreationDto.address());
    museum.setCollectionType(museumCreationDto.collectionType());
    museum.setSubject(museumCreationDto.subject());
    museum.setUrl(museumCreationDto.url());
    museum.setCoordinate(museumCreationDto.coordinate());

    return museum;
  }

  /**
   * get.
   */
  @GetMapping("/closest")
  public ResponseEntity<MuseumDto> getClosestMuseum(
      @RequestParam("lat") Double latitude,
      @RequestParam("lng") Double longitude,
      @RequestParam("max_dist_km") Double maxDistanceKm
  ) {
    Coordinate coordinate = new Coordinate(latitude, longitude);

    Museum closestMuseum = museumService.getClosestMuseum(coordinate, maxDistanceKm);

    if (closestMuseum != null) {
      MuseumDto closestMuseumDto = convertToMuseumDto(closestMuseum);
      return ResponseEntity.status(HttpStatus.OK).body(closestMuseumDto);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  private MuseumDto convertToMuseumDto(Museum museum) {
    return new MuseumDto(
        museum.getId(),
        museum.getName(),
        museum.getDescription(),
        museum.getAddress(),
        museum.getCollectionType(),
        museum.getSubject(),
        museum.getUrl(),
        museum.getCoordinate()
    );
  }

  /**
   * get.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Museum> getMuseum(@PathVariable Long id) {
    Museum museum = museumService.getMuseum(id);

    if (museum != null) {
      return ResponseEntity.status(HttpStatus.OK).body(museum);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}

package com.betrybe.museumfinder.controller;


import com.betrybe.museumfinder.dto.MuseumCreationDto;
import com.betrybe.museumfinder.dto.MuseumDto;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.service.MuseumServiceInterface;
import com.betrybe.museumfinder.util.ModelDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Museum Controller.
 */

@RestController
@RequestMapping("/museums")
public class MuseumController {

  @Autowired
  private final MuseumServiceInterface museumService;

  /**
   * Controller.
   */

  public MuseumController(MuseumServiceInterface museumService) {
    this.museumService = museumService;
  }

  /**
   * New Museum.
   */

  @PostMapping
  public ResponseEntity<MuseumDto> createMuseum(@RequestBody MuseumCreationDto newMuseum) {
    try {
      Museum museum = ModelDtoConverter.dtoToModel(newMuseum);
      Museum createMuseum = museumService.createMuseum(museum);
      MuseumDto createMuseumDto = ModelDtoConverter.modelToDto(createMuseum);
      return ResponseEntity.status(HttpStatus.CREATED).body(createMuseumDto);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Find de closest museum.
   */
  @GetMapping("/closest")
  public ResponseEntity<MuseumDto> findClosestMuseum(
      @RequestParam("lat") Double latitude,
      @RequestParam("lng") Double longitude,
      @RequestParam("max_dist_km") Double maxDistance) {

    Coordinate coordinate = new Coordinate(latitude, longitude);
    Museum closestMuseum = museumService.getClosestMuseum(coordinate, maxDistance);
    MuseumDto museumDto = ModelDtoConverter.modelToDto(closestMuseum);

    return ResponseEntity.ok(museumDto);
  }
}
package com.betrybe.museumfinder.service;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import com.betrybe.museumfinder.exception.InvalidCoordinateException;
import com.betrybe.museumfinder.exception.MuseumNotFoundException;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.util.CoordinateUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MuseumService.
 */
@Service
public class MuseumService implements MuseumServiceInterface {
  private final MuseumFakeDatabase museumDatabase;

  @Autowired
  public MuseumService(MuseumFakeDatabase museumDatabase) {
    this.museumDatabase = museumDatabase;
  }

  @Override
  public Museum getClosestMuseum(Coordinate coordinate, Double maxDistance) {
    if (!CoordinateUtil.isCoordinateValid(coordinate)) {
      throw new InvalidCoordinateException();
    }

    Optional<Museum> closestMuseumOptional = museumDatabase
        .getClosestMuseum(coordinate, maxDistance);

    if (closestMuseumOptional.isPresent()) {
      return closestMuseumOptional.get();
    }

    throw new MuseumNotFoundException();
  }

  @Override
  public Museum createMuseum(Museum museum) {
    if (!CoordinateUtil.isCoordinateValid(museum.getCoordinate())) {
      throw new InvalidCoordinateException();
    }

    Museum savedMuseum = museumDatabase.saveMuseum(museum);
    return savedMuseum;
  }

  @Override
  public Museum getMuseum(Long id) {
    Optional<Museum> foundMuseum = museumDatabase.getMuseum(id);

    if (foundMuseum.isPresent()) {
      return foundMuseum.get();
    }
    throw new MuseumNotFoundException();
  }
}


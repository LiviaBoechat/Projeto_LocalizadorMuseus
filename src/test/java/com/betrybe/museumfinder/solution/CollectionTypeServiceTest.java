package com.betrybe.museumfinder.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import com.betrybe.museumfinder.dto.CollectionTypeCount;
import com.betrybe.museumfinder.service.CollectionTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
class CollectionTypeServiceTest {

  @Autowired
  CollectionTypeService service;

  @MockBean
  MuseumFakeDatabase database;

  @Test
  public void testCountByCollectionTypesNoResult() {
    Mockito
        .when(database.countByCollectionType(any()))
        .thenReturn(0L);

    CollectionTypeCount collectionTypeCount = service.countByCollectionTypes("bla");

    assertEquals(collectionTypeCount.count(), 0L);
  }

  @Test
  public void testCountByCollectionTypes() {
    Mockito
        .when(database.countByCollectionType(any()))
        .thenReturn(100L);

    CollectionTypeCount collectionTypeCount = service.countByCollectionTypes("history");

    assertEquals(collectionTypeCount.count(), 100L);
  }
}

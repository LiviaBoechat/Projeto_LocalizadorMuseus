package com.betrybe.museumfinder.solution;

import com.betrybe.museumfinder.dto.CollectionTypeCount;
import com.betrybe.museumfinder.service.CollectionTypeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CollectionTypeControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  CollectionTypeService service;

  @Test
  @DisplayName("Testa a rota /collections/count/{type}")
  void testCountByCollectionTypesNotFound() throws Exception {
    Mockito
        .when(service.countByCollectionTypes(any()))
        .thenReturn(new CollectionTypeCount(new String[] {"bla"}, 0));

    mockMvc.perform(get("/collections/count/bla").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

    Mockito.verify(service).countByCollectionTypes(any());
  }

  @Test
  @DisplayName("Testa a rota /collections/count/{type}")
  void testCountByCollectionTypeFoundMultiply() throws Exception {
    Mockito
        .when(service.countByCollectionTypes(any()))
        .thenReturn(new CollectionTypeCount(new String[] {"history", "math"}, 2L));

    mockMvc.perform(get("/collections/count/history,math").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    Mockito.verify(service).countByCollectionTypes(any());
  }

  @Test
  @DisplayName("Testa a rota /collections/count/{type}")
  void testCountByCollectionTypeFoundSingle() throws Exception {
    Mockito
        .when(service.countByCollectionTypes(any()))
        .thenReturn(new CollectionTypeCount(new String[] {"history"}, 100L));

    mockMvc.perform(get("/collections/count/history").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    Mockito.verify(service).countByCollectionTypes(any());
  }

}



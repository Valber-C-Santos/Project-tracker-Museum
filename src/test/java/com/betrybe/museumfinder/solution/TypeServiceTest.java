package com.betrybe.museumfinder.solution;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import com.betrybe.museumfinder.dto.CollectionTypeCount;
import com.betrybe.museumfinder.service.CollectionTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test Service.
 */
public class TypeServiceTest {
  @Mock
  private MuseumFakeDatabase museumFakeDatabase;

  @BeforeEach
  void setUp() {
    museumFakeDatabase = mock(MuseumFakeDatabase.class);
    collectionTypeService = new CollectionTypeService(museumFakeDatabase);
  }

  @InjectMocks
  private CollectionTypeService collectionTypeService;

  @Test
  @DisplayName("Status 'ok' ao acessar a rota /collections/count/{typesList}.")
  public void statusOk() throws Exception {
    MockitoAnnotations.openMocks(this);

    String typeList = "war,guns";

    when(museumFakeDatabase.countByCollectionType("war")).thenReturn(253L);
    when(museumFakeDatabase.countByCollectionType("guns")).thenReturn(253L);

    CollectionTypeCount count = collectionTypeService.countByCollectionTypes(typeList);

    assertEquals(506, count.count());
  }

  @Test
  @DisplayName("Deve retornar contagem correta para vários tipos de coleção")
  void returnCorrectCountForMultipleCollectionTypes() {

    when(museumFakeDatabase.countByCollectionType("space")).thenReturn(228L);
    when(museumFakeDatabase.countByCollectionType("war")).thenReturn(253L);
    when(museumFakeDatabase.countByCollectionType("science")).thenReturn(250L);

    CollectionTypeCount count = collectionTypeService.countByCollectionTypes("space, war, science");

    assertEquals(731, count.count());
  }

}
package com.betrybe.museumfinder.solution;

import com.betrybe.museumfinder.dto.CollectionTypeCount;
import com.betrybe.museumfinder.service.CollectionTypeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

/**
 * Test Controller.
 */

@SpringBootTest
@AutoConfigureMockMvc
public class TypeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CollectionTypeService collectionTypeService;

  @Test
  @DisplayName("Deve retornar válido quando tipos de coleção são fornecidos")
  void returnCorrectCountTypesProvided() throws Exception {

    CollectionTypeCount countType = new CollectionTypeCount(new String[]{"space", "imag"}, 456);
    String typeList = "space,imag";

    when(collectionTypeService.countByCollectionTypes(typeList)).thenReturn(countType);

    mockMvc.perform(MockMvcRequestBuilders.get("/collections/count/" + typeList))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(456));
  }
  @Test
  @DisplayName("Deve retornar 404 quando nenhum tipo de coleção é fornecido")
  void returnNotFoundWhenNoCollectionTypesProvided() throws Exception {
    String typeList = "";

    when(collectionTypeService.countByCollectionTypes(typeList)).thenReturn(new CollectionTypeCount(new String[]{}, 0));

    mockMvc.perform(MockMvcRequestBuilders.get("/collections/count/" + typeList))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}

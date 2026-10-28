package controller;

import ch.tbz.recipe.planner.controller.RecipeController;
import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.mapper.RecipeEntityMapper;
import ch.tbz.recipe.planner.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
@ContextConfiguration(classes = {RecipeController.class, RecipeEntityMapper.class})
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService service;

    @MockBean
    private RecipeEntityMapper mapper;

    @Test
    void getRecipes_shouldReturnRecipes() throws Exception {
        // Testet, ob mehrere Rezepte vom Service gefunden werden können und korrekt in der HTTP-Response angezeigt werden.

        Recipe pizza = new Recipe(
                UUID.randomUUID(),
                "Pizza",
                "Pizza with tomato",
                "pizza.jpg",
                List.of()
        );

        Recipe pasta = new Recipe(
                UUID.randomUUID(),
                "Pasta",
                "Pasta with tomato sauce",
                "pasta.jpg",
                List.of()
        );

        //stubbing: definiert verhalten eines test objekts beim test
        when(service.getRecipes())
                .thenReturn(List.of(pizza, pasta));

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                // Prüft, ob genau zwei Rezepte zurückkommen.
                .andExpect(jsonPath("$.length()").value(2))
                // Prüft, ob das Pizza Rezept korrekt zurückkommt.
                .andExpect(jsonPath("$[0].name").value("Pizza"))
                .andExpect(jsonPath("$[0].description")
                        .value("Pizza with tomato"))
                // Prüft, ob das Pasta Rezept korrekt zurückkommt.
                .andExpect(jsonPath("$[1].name").value("Pasta"))
                .andExpect(jsonPath("$[1].description")
                        .value("Pasta with tomato sauce"));
    }

    @Test
    void getRecipes_shouldReturnEmptyList() throws Exception {
        // Testet, ob der Endpunkt eine leere JSON-Liste zurückgibt, wenn der Service keine Rezepte findet.

        when(service.getRecipes())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getRecipe_shouldReturnRecipe() throws Exception {
        // Testet, ob ein einzelnes Rezept anhand seiner ID geladen werden kann und die korrekten rezept details zurückgegeben werden.

        UUID id = UUID.randomUUID();

        Recipe recipe = new Recipe(
                id,
                "Pizza",
                "Pizza with tomato",
                "pizza.jpg",
                List.of()
        );

        when(service.getRecipeById(id))
                .thenReturn(recipe);

        mockMvc.perform(get("/api/recipes/recipe/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza"))
                .andExpect(jsonPath("$.description")
                        .value("Pizza with tomato"));
    }
    // TDD soll HttpStatus 201 Created liefern
    @Test
    void addRecipe_shouldReturn201Created() throws Exception {

        Recipe recipe = new Recipe();

        when(service.addRecipe(any(Recipe.class)))
                .thenReturn(recipe);

        String recipeJson = """
        {
          "name": "Pizza",
          "description": "Pizza mit Käse",
          "imageUrl": "pizza.jpg",
          "ingredients": [
            {
              "name": "Tomate",
              "comment": "Frisch",
              "unit": "PIECE",
              "amount": 5
            }
          ]
        }
        """;

        mockMvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeJson))
                .andExpect(status().isCreated());
    }
}
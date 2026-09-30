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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        Recipe recipe = new Recipe(
                UUID.randomUUID(),
                "Pizza",
                "Pizza with tomato",
                "pizza.jpg",
                List.of()
        );
        when(service.getRecipes())
                .thenReturn(List.of(recipe));
        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pizza"))
                .andExpect(jsonPath("$[0].description")
                        .value("Pizza with tomato"));
    }

    @Test
    void getRecipes_shouldReturnEmptyList() throws Exception {
        when(service.getRecipes())
                .thenReturn(List.of());
        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getRecipe_shouldReturnRecipe() throws Exception {
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
        mockMvc.perform(
                        get("/api/recipes/recipe/" + id)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza"))
                .andExpect(jsonPath("$.description")
                        .value("Pizza with tomato"));
    }
}


package mapper;

import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.entities.RecipeEntity;
import ch.tbz.recipe.planner.mapper.RecipeEntityMapper;
import ch.tbz.recipe.planner.mapper.RecipeEntityMapperImpl;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RecipeEntityMapperTest {
    private final RecipeEntityMapper mapper =
            new RecipeEntityMapperImpl();

    @Test
    void domainToEntity_shouldMapRecipe() {
        // Testet ob die Umwandlung von Recipe zu RecipeEntity funktioniert.
        UUID id = UUID.randomUUID();
        Recipe recipe = new Recipe(
                id,
                "Pizza",
                "Pizza mit Käse",
                "pizza.jpg",
                List.of()
        );
        RecipeEntity result = mapper.domainToEntity(recipe);
        // Prüft, ob die Daten korrekt übernommen wurden.
        assertEquals(id, result.getId());
        assertEquals("Pizza", result.getName());
        assertEquals("Pizza mit Käse", result.getDescription());
        assertEquals("pizza.jpg", result.getImageUrl());
        assertNotNull(result.getIngredients());
    }

    @Test
    void entityToDomain_shouldMapRecipe() {
        // Testet ob die Umwandlung von RecipeEntity zu Recipe auch funktioniert.
        UUID id = UUID.randomUUID();
        RecipeEntity entity = new RecipeEntity(
                id,
                "Pizza",
                "Pizza mit Käse",
                "pizza.jpg",
                List.of()
        );
        Recipe result = mapper.entityToDomain(entity);
        // Prüft, ob die Daten korrekt übernommen wurden.
        assertEquals(id, result.getId());
        assertEquals("Pizza", result.getName());
        assertEquals("Pizza mit Käse", result.getDescription());
        assertEquals("pizza.jpg", result.getImageUrl());
        assertNotNull(result.getIngredients());
    }
}



package mapper;

import ch.tbz.recipe.planner.domain.Ingredient;
import ch.tbz.recipe.planner.domain.Unit;
import ch.tbz.recipe.planner.entities.IngredientEntity;
import ch.tbz.recipe.planner.mapper.IngredientEntityMapper;
import ch.tbz.recipe.planner.mapper.IngredientEntityMapperImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IngredientEntityMapperTest {

    private final IngredientEntityMapper mapper =
            new IngredientEntityMapperImpl();


    @Test
    void domainToEntity_shouldMapIngredient() {

        UUID id = UUID.randomUUID();

        Ingredient ingredient = new Ingredient(
                id,
                "Tomato",
                "The red ones",
                Unit.PIECE,
                5
        );

        IngredientEntity result =
                mapper.domainToEntity(ingredient);

        assertEquals(id, result.getId());
        assertEquals("Tomato", result.getName());
        assertEquals("The red ones", result.getComment());
        assertEquals(Unit.PIECE, result.getUnit());
        assertEquals(5, result.getAmount());
    }


    @Test
    void entityToDomain_shouldMapIngredient() {

        UUID id = UUID.randomUUID();

        IngredientEntity entity = new IngredientEntity(
                id,
                "Tomato",
                "The big ones",
                Unit.PIECE,
                5
        );

        Ingredient result =
                mapper.entityToDomain(entity);

        assertEquals(id, result.getId());
        assertEquals("Tomato", result.getName());
        assertEquals("The big ones", result.getComment());
        assertEquals(Unit.PIECE, result.getUnit());
        assertEquals(5, result.getAmount());
    }


    @Test
    void entitiesToDomains_shouldMapList() {

        IngredientEntity entity1 = new IngredientEntity(
                UUID.randomUUID(),
                "Tomato",
                "Green tomato",
                Unit.PIECE,
                5
        );

        IngredientEntity entity2 = new IngredientEntity(
                UUID.randomUUID(),
                "Flour",
                "Oat flour",
                Unit.GRAMM,
                500
        );

        List<Ingredient> result =
                mapper.entitiesToDomains(
                        List.of(entity1, entity2)
                );

        assertEquals(2, result.size());
        assertEquals("Tomato", result.get(0).getName());
        assertEquals("Flour", result.get(1).getName());
    }


    @Test
    void domainsToEntities_shouldMapList() {

        Ingredient ingredient1 = new Ingredient(
                UUID.randomUUID(),
                "Tomato",
                "Red tomato",
                Unit.PIECE,
                5
        );

        Ingredient ingredient2 = new Ingredient(
                UUID.randomUUID(),
                "Flour",
                "Oat flour",
                Unit.GRAMM,
                500
        );

        List<IngredientEntity> result =
                mapper.domainsToEntities(
                        List.of(ingredient1, ingredient2)
                );

        assertEquals(2, result.size());
        assertEquals("Tomato", result.get(0).getName());
        assertEquals("Flour", result.get(1).getName());
    }
}

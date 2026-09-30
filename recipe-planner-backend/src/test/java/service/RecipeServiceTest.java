package service;

import ch.tbz.recipe.planner.domain.Recipe;
import ch.tbz.recipe.planner.entities.RecipeEntity;
import ch.tbz.recipe.planner.mapper.RecipeEntityMapper;
import ch.tbz.recipe.planner.repository.RecipeRepository;
import ch.tbz.recipe.planner.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository repository;

    @Mock
    private RecipeEntityMapper mapper;

    @InjectMocks
    private RecipeService service;


    @Test
    void getRecipes_shouldReturnRecipes() {

        RecipeEntity entity = new RecipeEntity();
        Recipe recipe = new Recipe();

        when(repository.findAll())
                .thenReturn(List.of(entity));

        when(mapper.entityToDomain(entity))
                .thenReturn(recipe);

        List<Recipe> result = service.getRecipes();

        assertEquals(1, result.size());
        assertSame(recipe, result.get(0));

        verify(repository).findAll();
        verify(mapper).entityToDomain(entity);
    }


    @Test
    void getRecipes_shouldReturnEmptyList() {

        when(repository.findAll())
                .thenReturn(List.of());

        List<Recipe> result = service.getRecipes();

        assertTrue(result.isEmpty());

        verify(repository).findAll();
    }


    @Test
    void getRecipeById_shouldReturnRecipe() {

        UUID id = UUID.randomUUID();

        RecipeEntity entity = new RecipeEntity();
        Recipe recipe = new Recipe();

        when(repository.findById(id))
                .thenReturn(Optional.of(entity));

        when(mapper.entityToDomain(entity))
                .thenReturn(recipe);

        Recipe result = service.getRecipeById(id);

        assertSame(recipe, result);

        verify(repository).findById(id);
        verify(mapper).entityToDomain(entity);
    }


    @Test
    void addRecipe_shouldSaveAndReturnRecipe() {

        Recipe recipe = new Recipe();
        RecipeEntity entity = new RecipeEntity();
        RecipeEntity savedEntity = new RecipeEntity();
        Recipe savedRecipe = new Recipe();

        when(mapper.domainToEntity(recipe))
                .thenReturn(entity);

        when(repository.save(entity))
                .thenReturn(savedEntity);

        when(mapper.entityToDomain(savedEntity))
                .thenReturn(savedRecipe);

        Recipe result = service.addRecipe(recipe);

        assertSame(savedRecipe, result);

        verify(mapper).domainToEntity(recipe);
        verify(repository).save(entity);
        verify(mapper).entityToDomain(savedEntity);
    }
}

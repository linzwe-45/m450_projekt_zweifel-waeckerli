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
        // Testet, ob alle Rezepte aus dem Repository geladen werden können.
        RecipeEntity entity = new RecipeEntity();
        Recipe recipe = new Recipe();
        // Simuliert ein gefundenes Rezept und dessen Umwandlung.
        when(repository.findAll())
                .thenReturn(List.of(entity));

        when(mapper.entityToDomain(entity))
                .thenReturn(recipe);

        List<Recipe> result = service.getRecipes();

        // Testet, ob genau ein Rezept mit erwartetem Objekt zurückkommt.
        assertEquals(1, result.size());
        assertSame(recipe, result.get(0));

        // Prüft, ob Repository und Mapper aufgerufen werden können.
        verify(repository).findAll();
        verify(mapper).entityToDomain(entity);
    }

    @Test
    void getRecipes_shouldReturnEmptyList() {
        // Testet, ob eine leere Liste zurückkommt, wenn kein rezept vorhanden ist.

        // Simuliert ein Repository ohne gespeicherte Rezepte.
        when(repository.findAll())
                .thenReturn(List.of());

        List<Recipe> result = service.getRecipes();

        // Prüft, ob die Ergebnisliste leer ist.
        assertTrue(result.isEmpty());

        verify(repository).findAll();
    }

    @Test
    void getRecipeById_shouldReturnRecipe() {
        // Testet, ob ein Rezept anhand seiner Recipe ID gefunden werden kann.
        UUID id = UUID.randomUUID();

        RecipeEntity entity = new RecipeEntity();
        Recipe recipe = new Recipe();

        // Simuliert ein gefundenes Rezept
        when(repository.findById(id))
                .thenReturn(Optional.of(entity));

        when(mapper.entityToDomain(entity))
                .thenReturn(recipe);

        Recipe result = service.getRecipeById(id);

        // Prüft, ob das erwartete Rezept zurückgegeben wird.
        assertSame(recipe, result);

        verify(repository).findById(id);
        verify(mapper).entityToDomain(entity);
    }

    @Test
    void addRecipe_shouldSaveAndReturnRecipe() {
        // Testet, ob ein neues Rezept in eine Entity umgewandelt, gespeichert und zurückgegeben wird.
        Recipe recipe = new Recipe();
        RecipeEntity entity = new RecipeEntity();
        RecipeEntity savedEntity = new RecipeEntity();
        Recipe savedRecipe = new Recipe();

        // Simuliert die Umwandlung des Rezepts in eine Entity.
        when(mapper.domainToEntity(recipe))
                .thenReturn(entity);

        // Simuliert das Speichern und die Rückgabe der gespeicherten Entity.
        when(repository.save(entity))
                .thenReturn(savedEntity);

        when(mapper.entityToDomain(savedEntity))
                .thenReturn(savedRecipe);

        Recipe result = service.addRecipe(recipe);

        // Prüft, ob das gespeicherte Rezept zurückgegeben wird.
        assertSame(savedRecipe, result);

        // Prüft, ob Umwandlung, Speichern und Rückumwandlung ausgeführt wurden.
        verify(mapper).domainToEntity(recipe);
        verify(repository).save(entity);
        verify(mapper).entityToDomain(savedEntity);
    }

    //TDD - Test Driven Development
    @Test
    void getRecipeById_shouldThrowExceptionWhenRecipeDoesNotExist() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                java.util.NoSuchElementException.class,
                () -> service.getRecipeById(id)
        );

        verify(repository).findById(id);
        verifyNoInteractions(mapper);
    }
}
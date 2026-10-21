package ch.tbz.recipe.planner.service;

import ch.tbz.recipe.planner.entities.RecipeEntity;
import ch.tbz.recipe.planner.mapper.RecipeEntityMapper;
import ch.tbz.recipe.planner.repository.RecipeRepository;
import ch.tbz.recipe.planner.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RecipeService {

    RecipeEntityMapper mapper;

    RecipeRepository repository;

    public RecipeService(RecipeEntityMapper mapper, RecipeRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<Recipe> getRecipes() {
        List<RecipeEntity> recipesEntities = repository.findAll();
        return recipesEntities.stream().map(mapper::entityToDomain).toList();
    }

   // public Recipe getRecipeById(UUID recipeId) {
   //     return mapper.entityToDomain(repository.findById(recipeId).orElse(null));
   // } // OLD version (before TDD)
   // new version for TDD
    public Recipe getRecipeById(UUID recipeId) {
       return mapper.entityToDomain(
               repository.findById(recipeId)
                       .orElseThrow(() ->
                               new java.util.NoSuchElementException(
                                       "Rezept mit ID " + recipeId + " wurde nicht gefunden."
                               )
                       )
       );
   }

    public Recipe addRecipe(Recipe recipe) {
        var createdRecipe = repository.save(mapper.domainToEntity(recipe));
        return mapper.entityToDomain(createdRecipe);
    }
}

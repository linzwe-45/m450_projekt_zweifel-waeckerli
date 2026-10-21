import React, {useState} from 'react';
import './AddRecipe.css';
import { useForm } from "react-hook-form";
import {Form, Button, Col, Row} from 'react-bootstrap';

import axios from "axios";
import AddIngredient from "../AddIngredient/AddIngredient";

function AddRecipe() {
    const [ ingredients, setIngredients ] = useState([])
    const [formData, setFormData] = useState({
        "name": '',
        "description": '',
        "imageUrl": '',
        "ingredients": [],
        "id": null
    })

    const [listId, setListId] = useState(1)

    const {
        register,
        handleSubmit,
        watch,
        formState: {errors},
    } = useForm()

    const onSubmit = async (data) => {
        const recipe = {
            ...data,
            ingredients: formData.ingredients
        };

        try {
            const response = await axios.post(
                'http://localhost:8080/api/recipes',
                recipe
            );

            console.log('Rezept gesaved:', response.data);
            alert('Rezept gespeichert');
        } catch (error) {
            console.error('Error save:', error);
            alert('Rezept nicht gespeichert');
        }
    };


    const addIngredient = () => {

        setFormData(({...formData, ingredients: [
                ...formData.ingredients, {
                    listId: listId,
                    ingredient: '',
                    unit: 'PIECE',
                    quantity: ''
                }
            ]}))
        setListId(listId + 1)

    }
    const updateIngredient = (ingredientObj) => {
        const updatedIngredients = formData.ingredients.map((ingredient) => {
            if (ingredient.listId === ingredientObj.listId) {
                return ingredientObj
            }
            return ingredient
        })
        setFormData({...formData, ingredients: updatedIngredients})
    }

    const removeIngredient = (ingredientObj) => {
        const updatedIngredients = formData.ingredients.filter((ingredient) => ingredient.listId !== ingredientObj.listId)
        setFormData({...formData, ingredients: updatedIngredients})
    }

    const renderIngredients = formData.ingredients.map(ingredient => <AddIngredient
        key={ingredient.listId}
        ingredient={ingredient}
        ingredients={ingredients}
        listId={listId - 1}
        updateIngredient={updateIngredient}
        removeIngredient={removeIngredient}
    />)


    return (
        <div className="bg">
            <div className="m-3">
                <h1 className="h3 bg-dark text-bg-primary mt-2">
                    Add Recipe
                </h1>

                <Form onSubmit={handleSubmit(onSubmit)}>
                    <div className="mb-1">
                        <label htmlFor="recipeName">Recipe Name:</label>
                        <input
                            id="recipeName"
                            className="form-control"
                            placeholder="Name"
                            {...register("name", { required: true })}
                        />
                    </div>

                    <div className="mb-1">
                        <label htmlFor="description">Description:</label>
                        <input
                            id="description"
                            className="form-control"
                            placeholder="Description"
                            {...register("description")}
                        />
                    </div>

                    <div className="mb-5">
                        <label htmlFor="imageUrl">Image URL:</label>
                        <input
                            id="imageUrl"
                            className="form-control"
                            placeholder="URL"
                            {...register("imageUrl")}
                        />
                    </div>

                    <Row>
                        <Col>Ingredient</Col>
                        <Col>Unit</Col>
                        <Col>Quantity</Col>
                        <Col xs={1}></Col>
                    </Row>

                    <hr />

                    {renderIngredients}

                    <Button
                        variant="warning"
                        onClick={addIngredient}
                        className="mt-1 mb-3"
                        type="button"
                    >
                        Add Ingredient
                    </Button>

                    <div>
                        <Button
                            variant="primary"
                            type="submit"
                            className="mb-5"
                        >
                            Submit
                        </Button>
                    </div>
                </Form>
            </div>
        </div>
    );
}

export default AddRecipe;

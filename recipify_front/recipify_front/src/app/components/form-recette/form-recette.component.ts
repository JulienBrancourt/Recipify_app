import { Component, OnInit } from '@angular/core';
import {FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { Units } from '../../utils/enum/Units';
import {Ingredient, Recette} from "../../utils/types/recetteType";

@Component({
  selector: 'app-form-recette',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './form-recette.component.html',
  styleUrls: ['./form-recette.component.css']
})
export class FormRecetteComponent  {
  recette: Recette[] = [];
  unit = Object.values(Units);

  recetteForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    ingredients: new FormArray([]),
    instruction: new FormControl('', [Validators.required]),
    prepTime: new FormControl('', [Validators.required, Validators.pattern(/^\d+$/)]), // Ensuring it's a number
    isPublic: new FormControl(false)
  });

  constructor() {
    const stored = localStorage.getItem('recettes');
    if(stored) {
      this.recette = JSON.parse(stored)
    }
  }



  get ingredients() {
    return this.recetteForm.get('ingredients') as FormArray;
  }

  addIngredient(): void {
    this.ingredients.push(new FormGroup({
      ingredientName: new FormControl('', [Validators.required]),
      quantity: new FormControl(0, [Validators.required, Validators.min(0)]),
      unit: new FormControl('', [Validators.required]),
      calorie: new FormControl(0, [Validators.required, Validators.min(0)]) // Include calorie
    }));
  }



  handleSubmit() {
    if (this.recetteForm.invalid) {
      return;
    }

    const newRecette: Recette = {
      title: this.recetteForm.value.title || '',
      slug: '',
      instruction: this.recetteForm.value.instruction || '',
      calorie: 0,
      serving: 0,
      ingredients: (this.recetteForm.value.ingredients as any[]).map(ingredient => ({
        ingredientName: ingredient.ingredientName || '',
        quantity: ingredient.quantity || 0,
        unit: ingredient.unit || '',
        calorie: ingredient.calorie || 0
      }))
    };

    this.recette.push(newRecette);
    console.log('recette enregistrée', this.recetteForm.value)
    localStorage.setItem('recettes', JSON.stringify(this.recette));
    this.recetteForm.reset();
    this.ingredients.clear();
  }
}

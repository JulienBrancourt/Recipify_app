import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

import {Recette} from "../../utils/types/recetteType";
import {CommonModule} from "@angular/common";
import {GetDataService} from "../../service/getData.service";
import {RecetteService} from "../../service/recette.service";
import {UnitService} from "../../service/unit-service.service";

@Component({
  selector: 'app-form-recette',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './form-recette.component.html',
  styleUrls: ['./form-recette.component.css']
})
export class FormRecetteComponent implements OnInit {
  recette: Recette[] = [];
  ingredientsArray: string[] = [];
  units: string[] = [];

  constructor(private dataService: GetDataService,
              private recetteService: RecetteService,
              private unitService: UnitService) {
  }

  recetteForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    ingredients: new FormArray([]),
    instruction: new FormControl('', [Validators.required]),
    prepTime: new FormControl('', [Validators.required, Validators.pattern(/^\d+$/)]), // Ensuring it's a number
    isPublic: new FormControl(false)
  });

  ngOnInit(): void {
    this.dataService.getIngredients().subscribe({
      next: (ingredientsArray) => {
        this.ingredientsArray = ingredientsArray.map((i: any) => i.ingredientName);
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des ingrédients:', err);
      }
    });

    this.dataService.getUnits().subscribe({
      next: (units) => {
        this.units = units;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des unités:', err);
      }
    });
  }

  get ingredients(): FormArray {
    return this.recetteForm.get('ingredients') as FormArray;
  }

  getUnitDisplayName(unit: string): string {
    return this.unitService.getDisplayName(unit);
  }


  addIngredient(): void {
    this.ingredients.push(new FormGroup({
      ingredientName: new FormControl('', [Validators.required]),
      quantity: new FormControl(0, [Validators.required, Validators.min(0)]),
      unit: new FormControl('', [Validators.required]),
    }));
  }


  handleSubmit() {
    if (this.recetteForm.invalid) {
      console.error('Formulaire invalide');
      return;
    }

    const newRecette: Recette = {
      title: this.recetteForm.value.title || '',
      slug: '',  // You may need a slug generation logic
      instruction: this.recetteForm.value.instruction || '',
      calorie: 0,  // To be calculated in the backend
      serving: 1,
      time: Number(this.recetteForm.value.prepTime) || 0,
      recipeIngredients: (this.recetteForm.value.ingredients as any[]).map(ingredient => ({
        ingredient: {
          ingredientName: ingredient.ingredientName || '',
          calorie: 0,
        },
        quantity: ingredient.quantity || 0,
        unit: ingredient.unit || '',
      })),
      public: this.recetteForm.value.isPublic || false,
    };

    console.log('Nouvelle recette:', newRecette);

    this.recetteService.createRecipe(newRecette).subscribe({
      next: (createdRecette) => {
        console.log('Recette créée:', createdRecette);
      },
      error: (err) => {
        console.error('Erreur lors de la création de la recette:', err);
      },
      complete: () => {
        this.recetteForm.reset();
        this.ingredients.clear();
      }
    });
  }
}


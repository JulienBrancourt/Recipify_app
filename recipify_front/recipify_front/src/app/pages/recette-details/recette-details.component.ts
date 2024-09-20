import {Component, OnInit} from '@angular/core';
import {Ingredient, Recette, RecipeIngredient} from "../../utils/types/recetteType";
import {ActivatedRoute} from "@angular/router";
import {RecetteComponent} from "../recette/recette.component";
import {GetDataService} from "../../service/getData.service";
import {HttpClient} from "@angular/common/http";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-recette-details',
  standalone: true,
  imports: [
    NgIf,
    NgForOf
  ],
  templateUrl: './recette-details.component.html',
  styleUrl: './recette-details.component.css'
})
export class RecetteDetailsComponent implements OnInit{
  recette!: Recette | undefined;


  constructor(private route: ActivatedRoute, private getDataService: GetDataService) {
  }

  ngOnInit():void {
    const slug = this.route.snapshot.paramMap.get('slug')!;

    this.getDataService.getRecipeBySlug(slug).subscribe({
      next: (recette) => {
        console.log('Recette:', recette);
        // this.recette =recette;
        this.recette = {...recette,
          recipeIngredients: recette.recipeIngredients.map((ingredient: Ingredient) => ({
            ingredient: {
              ingredientName: ingredient.ingredientName,
              calorie: ingredient.calorie,
            },
            quantity: recette.recipeIngredients.quantity,
            unit: recette.recipeIngredients[0].unit,

          }))}
      },
      error: (err) => {
        console.error('Erreur lors de la récupération de la recette:', err);
      }
    });

    // Je récupères les informations de la recette
    // Je les passes au fronte


  }
}

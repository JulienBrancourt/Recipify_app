import { Component, Input, Output, EventEmitter, OnInit, ChangeDetectorRef } from '@angular/core';
import { Recette } from '../../utils/types/recetteType';
import { FavorisService } from '../../service/favoris.service';
import { NgClass, NgForOf, NgIf } from '@angular/common';
import { Router } from '@angular/router';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdminService } from "../../service/admin.service";
import { GetDataService } from "../../service/getData.service";
import { UnitService } from "../../service/unit-service.service";

@Component({
  selector: 'app-recette-card',
  standalone: true,
  imports: [NgClass, NgIf, ReactiveFormsModule, NgForOf],
  templateUrl: './recette-card.component.html',
  styleUrls: ['./recette-card.component.css']
})
export class RecetteCardComponent implements OnInit {
  @Input() recette!: Recette;
  @Input() view: 'global' | 'favorite' | 'admin' = 'global';

  @Output() favoriChanged = new EventEmitter<void>();
  @Output() recetteDeleted = new EventEmitter<string>();

  isEditing: boolean = false;
  editForm!: FormGroup;
  ingredientsArray: string[] = [];
  units: string[] = [];

  constructor(
    private favorisService: FavorisService,
    private router: Router,
    private dataService: GetDataService,
    private unitService: UnitService,
    private adminService: AdminService,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
  }

  ngOnInit() {
    this.initEditForm();

    if (this.view === 'admin') {
      this.loadIngredientAndUnitData();
    }
  }


  initEditForm(): void {
    this.editForm = this.fb.group({
      title: [this.recette.title, Validators.required],
      instruction: [this.recette.instruction, Validators.required],
      ingredients: this.fb.array(
        this.recette.recipeIngredients
          ? this.recette.recipeIngredients.map(ingredient => this.fb.group({
            ingredientName: [ingredient?.ingredient?.ingredientName || '', Validators.required],
            quantity: [ingredient?.quantity || 0, [Validators.required, Validators.min(1)]],
            unit: [ingredient?.unit || '', Validators.required]
          }))
          : []
      )
    });
  }


  loadIngredientAndUnitData(): void {

    this.dataService.getIngredients().subscribe({
      next: (ingredients) => {
        this.ingredientsArray = ingredients.map((i: any) => i.ingredientName);
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
    return this.editForm.get('ingredients') as FormArray;
  }


  getUnitDisplayName(unit: string): string {
    return this.unitService.getDisplayName(unit);
  }


  addToFavorite(slug: string): void {
    this.favorisService.addFavori(slug).subscribe({
      next: () => this.favoriChanged.emit(),
      error: (err) => console.error('Erreur lors de l\'ajout de la recette aux favoris:', err)
    });
  }


  toggleEdit(): void {
    this.isEditing = !this.isEditing;
    this.cdr.detectChanges();
  }


  updateRecette(): void {
    if (this.editForm.valid) {
      const updatedIngredients = this.editForm.value.ingredients.filter((ingredient: any) => {
        return ingredient.ingredientName && ingredient.quantity && ingredient.unit;
      });

      const updatedRecette: Recette = {
        ...this.recette,
        title: this.editForm.value.title,
        instruction: this.editForm.value.instruction,
        recipeIngredients: updatedIngredients.map((ing: any) => ({
          ingredient: {ingredientName: ing.ingredientName},
          quantity: ing.quantity,
          unit: ing.unit
        }))
      };

      this.adminService.updateRecette(this.recette.slug, updatedRecette).subscribe({
        next: (recette) => {
          console.log('Recette mise à jour avec succès:', recette);
          this.isEditing = false;
          this.recette = recette;
          this.cdr.detectChanges();
        },
        error: (err) => console.error('Erreur lors de la mise à jour de la recette:', err)
      });
    } else {
      console.error('Formulaire invalide');
    }
  }


  removeFromFavorites(slug: string): void {
    this.favorisService.removeFavori(slug).subscribe({
      next: () => this.favoriChanged.emit(),
      error: (err) => console.error('Erreur lors de la suppression des favoris:', err)
    });
  }


  detailsRecipe(): void {
    this.router.navigate(['/recette-details', this.recette.slug])
      .then(success => {
        if (success) {
          console.log('Navigation réussie vers /recette-details');
        } else {
          console.error('Échec de la navigation vers /recette-details');
        }
      })
      .catch(error => {
        console.error('Erreur lors de la navigation vers /recette-details', error);
      });
  }


  deleteRecipe(): void {
    if (confirm(`Êtes-vous sûr de vouloir supprimer la recette "${this.recette.slug}" ?`)) {
      this.adminService.deleteRecettes(this.recette.slug).subscribe({
        next: () => {
          console.log('Recette supprimée avec succès:', this.recette.slug);
          this.recetteDeleted.emit(this.recette.slug);
        },
        error: (err) => console.error('Erreur lors de la suppression de la recette:', err),
        complete: () => window.location.reload()
      });
    }
  }


  addIngredientInput(): void {
    const ingredientGroup = this.fb.group({
      ingredientName: ['', Validators.required],
      quantity: [0, [Validators.required, Validators.min(1)]],
      unit: ['', Validators.required]
    });
    this.ingredients.push(ingredientGroup);
  }
}


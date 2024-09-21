import {Component, Input, Output, EventEmitter, OnInit, ChangeDetectorRef} from '@angular/core';
import { Recette } from '../../utils/types/recetteType';
import { FavorisService } from '../../service/favoris.service';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import { Router } from '@angular/router';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {AdminService} from "../../service/admin.service";

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
  ingredientsArray: string[] = []

  constructor(
    private favorisService: FavorisService,
    private router: Router,
    private adminService: AdminService,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
  }

  ngOnInit() {
    this.editForm = this.fb.group({
      title: [this.recette.title],
      instruction: [this.recette.instruction],
      ingredients: this.fb.array(this.recette.recipeIngredients ?
        this.recette.recipeIngredients.map(ingredient => this.fb.group({
          ingredientName: [ingredient?.ingredient?.ingredientName || ''],
          quantity: [ingredient?.quantity || 0],
          unit: [ingredient?.unit || '']
        }))
        : []
      )
    });
  }


  get ingredients(): FormArray {
    return this.editForm.get('ingredients') as FormArray;
  }


  addToFavorite(slug: string) {
    this.favorisService.addFavori(slug).subscribe({
      next: () => {
        this.favoriChanged.emit();
      },
      error: (err) => {
        console.error('Erreur lors de l\'ajout de la recette aux favoris:', err);
      }
    });
  }

  toggleEdit() {
    setTimeout(() => {
      this.isEditing = !this.isEditing;
      this.cdr.detectChanges();
    });
  }


  updateRecette() {
    if (this.editForm.valid) {
      const updatedRecette = {
        ...this.recette,
        ...this.editForm.value
      };

      this.adminService.updateRecette(this.recette.slug, updatedRecette).subscribe({
        next: (recette) => {
          console.log('Recette mise à jour avec succès:', recette);

          this.isEditing = false;
          this.recette = recette;
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Erreur lors de la mise à jour de la recette:', err);
        }
      });
    }
  }


  removeFromFavorites(title: string) {
    this.favorisService.removeFavori(title);
    this.favoriChanged.emit();
  }

  detailsRecipe() {
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

  deleteRecipe() {
    if (confirm(`Êtes-vous sûr de vouloir supprimer la recette "${this.recette.slug}" ?`)) {
      this.adminService.deleteRecettes(this.recette.slug).subscribe({
        next: () => {
          console.log('Recette supprimée avec succès:', this.recette.slug);
          this.recetteDeleted.emit(this.recette.slug);
        },
        error: (err) => {
          console.error('Erreur lors de la suppression de la recette:', err);
        }
      });
    }
  }

}

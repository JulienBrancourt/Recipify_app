import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Recette } from '../../utils/types/recetteType';
import { FavorisService } from '../../service/favoris.service';
import { NgClass } from '@angular/common';
import { Router } from '@angular/router';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-recette-card',
  standalone: true,
  imports: [NgClass],
  templateUrl: './recette-card.component.html',
  styleUrls: ['./recette-card.component.css']
})
export class RecetteCardComponent {
  @Input() recette!: Recette;
  @Input() view: 'global' | 'favorite' | 'admin' = 'global';

  @Output() favoriChanged = new EventEmitter<void>();
  @Output() recetteDeleted = new EventEmitter<string>();

  constructor(
    private favorisService: FavorisService,
    private router: Router,
    private adminService: AdminService
  ) {}

  addToFavorite(slug: string) {
    this.favorisService.addFavori(slug).subscribe({
      next: () => {
        this.favoriChanged.emit();
      },
      error: (err) => {
        console.error('Erreur lors de l\'ajout de la recette aux favoris:', err);
      },
      complete: () => {
        console.log('Favoris Ajouté');
      }
    });
  }

  updateRecette() {
    const slug = this.recette.slug;
    this.adminService.updateRecette(slug, this.recette).subscribe({
      next: (updatedRecette) => {
        console.log('Recette mise à jour avec succès:', updatedRecette);
      },
      error: (err) => {
        console.error('Erreur lors de la mise à jour de la recette:', err);
      }
    });
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
    const slug = this.recette.slug;
    if (confirm(`Êtes-vous sûr de vouloir supprimer la recette "${this.recette.slug}" ?`)) {
      this.adminService.deleteRecettes(slug).subscribe({
        next: () => {
          console.log('Recette supprimée avec succès:', slug);
          this.recetteDeleted.emit(slug);
        },
        error: (err) => {
          console.error('Erreur lors de la suppression de la recette:', err);
        }
      });
    }
  }
}

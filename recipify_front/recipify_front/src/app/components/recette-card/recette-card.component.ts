import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Recette } from "../../utils/types/recetteType";
import { FavorisService } from "../../service/favoris.service";
import {NgClass} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-recette-card',
  standalone: true,
  imports: [
    NgClass
  ],
  templateUrl: './recette-card.component.html',
  styleUrls: ['./recette-card.component.css']
})
export class RecetteCardComponent {
  @Input() recette!: Recette;
  @Input() view: 'global' | 'favorite' | 'admin' = 'global';

  @Output() favoriChanged = new EventEmitter<void>();

  constructor(private favorisService: FavorisService, private router: Router) {}

  addToFavorite() {
    if (this.favorisService.isInFavoris(this.recette)) {
      alert('Cette recette est déjà dans vos favoris');
    } else {
      this.favorisService.addFavori(this.recette);
      alert('Recette ajoutée aux favoris');
      this.favoriChanged.emit();
    }
  }

  removeFromFavorites(title: string) {
    this.favorisService.removeFavori(title);
    alert('Recette supprimée des favoris');
    this.favoriChanged.emit();
  }


  detailsRecipe() {
    console.log('Naviguer vers /recette-details avec slug:', this.recette.slug);
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
}

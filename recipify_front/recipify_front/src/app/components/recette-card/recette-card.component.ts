import { Component } from '@angular/core';

@Component({
  selector: 'app-recette-card',
  standalone: true,
  imports: [],
  templateUrl: './recette-card.component.html',
  styleUrl: './recette-card.component.css'
})
export class RecetteCardComponent {
  addToFavorites() {
    alert('Recette ajoutée aux favoris !');
  }

  shareRecipe() {
    alert('Recette partagée !');
  }
}

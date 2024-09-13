import { Component } from '@angular/core';

@Component({
  selector: 'app-recette',
  standalone: true,
  imports: [],
  templateUrl: './recette.component.html',
  styleUrl: './recette.component.css'
})
export class RecetteComponent {

  addToFavorites() {
    alert('Recette ajoutée aux favoris !');
  }

  shareRecipe() {
    alert('Recette partagée !');
  }

}

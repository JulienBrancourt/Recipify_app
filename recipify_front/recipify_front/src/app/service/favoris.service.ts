import { Injectable } from '@angular/core';
import {Recette} from "../utils/types/recetteType";

@Injectable({
  providedIn: 'root'
})
export class FavorisService {
  favoris: Recette[] = [];
  constructor() { }

  addFavori(recette: Recette) {
    const isExisting = this.favoris.some(fav => fav.title === recette.title)
    if (!isExisting) {
      this.favoris.push(recette);
      localStorage.setItem('favoris', JSON.stringify(this.favoris));
    } else {
      alert('Cette recette est déjà dans vos favoris');
    }
  }

  getFavoris(): Recette[] {
    return this.favoris;
  }

  removeFavori(title: string) {
    this.favoris = this.favoris.filter(fav => fav.title!== title);
    localStorage.setItem('favoris', JSON.stringify(this.favoris));
  }
}

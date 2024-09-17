import { Injectable } from '@angular/core';
import {Recette} from "../utils/types/recetteType";

@Injectable({
  providedIn: 'root'
})
export class FavorisService {
  favoris: Recette[] = [];
  constructor() {
    this.loadFavoris();
  }

  loadFavoris() {
    const storedFavorites = localStorage.getItem('favoris');
    if (storedFavorites) {
      this.favoris = JSON.parse(storedFavorites);
    }
  }

  addFavori(recette: Recette) {
    if (!this.isInFavoris(recette)) {
      this.favoris.push(recette);
      localStorage.setItem('favoris', JSON.stringify(this.favoris));
    }
  }

  isInFavoris(recette: Recette): boolean {
    return this.favoris.some(fav => fav.title === recette.title);
  }

  getFavoris(): Recette[] {
    return this.favoris;
  }

  removeFavori(title: string) {
    this.favoris = this.favoris.filter(fav => fav.title!== title);
    localStorage.setItem('favoris', JSON.stringify(this.favoris));
  }
}

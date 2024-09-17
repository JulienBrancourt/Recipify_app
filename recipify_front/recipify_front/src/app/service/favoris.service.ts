import { Injectable } from '@angular/core';
import { Recette } from "../utils/types/recetteType";
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FavorisService {
  private favorisSubject = new BehaviorSubject<Recette[]>([]);
  favoris$ = this.favorisSubject.asObservable();

  constructor() {
    this.loadFavoris();
  }

  loadFavoris() {
    const storedFavorites = localStorage.getItem('favoris');
    if (storedFavorites) {
      this.favorisSubject.next(JSON.parse(storedFavorites));
    }
  }

  addFavori(recette: Recette) {
    const favoris = this.favorisSubject.getValue();
    if (!this.isInFavoris(recette)) {
      favoris.push(recette);
      localStorage.setItem('favoris', JSON.stringify(favoris));
      this.favorisSubject.next(favoris);
    }
  }

  isInFavoris(recette: Recette): boolean {
    return this.favorisSubject.getValue().some(fav => fav.title === recette.title);
  }

  getFavoris(): Recette[] {
    return this.favorisSubject.getValue();
  }

  removeFavori(title: string) {
    const favoris = this.favorisSubject.getValue().filter(fav => fav.title !== title);
    localStorage.setItem('favoris', JSON.stringify(favoris));
    this.favorisSubject.next(favoris);
  }
}

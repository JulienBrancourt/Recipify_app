import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Recette } from "../utils/types/recetteType";
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class FavorisService {
  private apiUrl = environment.apiUrl;
  private favorisSubject = new BehaviorSubject<Recette[]>([]);
  favoris = this.favorisSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadFavoris();
  }

  loadFavoris() {
    this.http.get<Recette[]>(`${this.apiUrl}/userFavoriteRecipes`).subscribe(
      favoris => this.favorisSubject.next(favoris),
      error => console.error('Error loading favorites:', error)
    );
  }

  addFavori(recette: Recette): Observable<Recette> {
    return this.http.post<Recette>(`${this.apiUrl}/userFavoriteRecipe`, recette).pipe(
      tap(() => {
        const favoris = this.favorisSubject.getValue();
        if (!this.isInFavoris(recette)) {
          favoris.push(recette);
          this.favorisSubject.next(favoris);
        }
      })
    );
  }

  isInFavoris(recette: Recette): boolean {
    return this.favorisSubject.getValue().some(fav => fav.title === recette.title);
  }

  getFavoris(): Observable<Recette[]> {
    return this.favoris;
  }

  removeFavori(title: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/userFavoriteRecipe/${title}`).pipe(
      tap(() => {
        const favoris = this.favorisSubject.getValue().filter(fav => fav.title !== title);
        this.favorisSubject.next(favoris);
      })
    );
  }
}

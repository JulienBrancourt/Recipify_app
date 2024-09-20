import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, catchError, Observable, tap} from 'rxjs';
import {Recette} from "../utils/types/recetteType";
import {environment} from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class FavorisService {
  private apiUrl = environment.apiUrl;
  private favorisSubject = new BehaviorSubject<Recette[]>([]);
  favoris$ = this.favorisSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadFavoris();
  }

  loadFavoris(): void {
    this.http.get<Recette[]>(`${this.apiUrl}/favorite`).subscribe({
      next: (favoris) => this.favorisSubject.next(favoris),
      error: (err) => console.error('Error loading favorites:', err)
    });
  }

  addFavori(recette: Recette): Observable<any> {
    console.log('Slug de la recette:', recette.slug);
    return this.http.post<any>(`${this.apiUrl}/userFavoriteRecipe`, recette).pipe(
      tap(() => {
        console.log('Recette ajoutée aux favoris:', recette);
        this.loadFavoris();
      }),
      catchError(error => {
        console.error('Erreur lors de l\'ajout de la recette aux favoris:', error);
        return new Observable<any>();
      })
    );
  }
  isInFavoris(recette: Recette): boolean {
    return this.favorisSubject.getValue().some(fav => fav.title === recette.title);
  }



  removeFavori(title: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/favorite/${title}`).pipe(
      tap(() => {
        this.loadFavoris();
      })
    );
  }
}

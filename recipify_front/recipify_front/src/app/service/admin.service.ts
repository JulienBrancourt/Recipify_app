import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { BehaviorSubject, Observable, catchError, of, tap } from 'rxjs';
import { Recette } from '../utils/types/recetteType';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = environment.apiUrl;
  private adminSubject = new BehaviorSubject<Recette[]>([]);

  constructor(private http: HttpClient) {}

  updateRecette(slug: string, recette: Recette): Observable<Recette> {
    return this.http.put<Recette>(`${this.apiUrl}/admin/${slug}`, recette).pipe(
      tap((updatedRecette) => {
        const currentRecettes = this.adminSubject.value;
        const index = currentRecettes.findIndex(r => r.slug === slug);
        if (index !== -1) {
          currentRecettes[index] = updatedRecette;
        } else {
          currentRecettes.push(updatedRecette);
        }
        this.adminSubject.next(currentRecettes);
      }),
      catchError((error) => {
        console.error('Error updating recette', error);
        return of(recette);
      })
    );
  }

  deleteRecettes(slug: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/admin/${slug}`).pipe(
      tap(() => {
        const currentRecettes = this.adminSubject.value;
        const index = currentRecettes.findIndex(r => r.slug === slug);
        if (index !== -1) {
          currentRecettes.splice(index, 1);
        }
        this.adminSubject.next(currentRecettes);
      }),
      catchError((error) => {
        console.error('Error deleting recette', error);
        throw error;
      })
    );
  }
}

import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FridgeService {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  addToFridge(ingredientsData: any): Observable<any> {
    console.log('Adding ingredients to fridge:', ingredientsData);
    return this.http.post<any>(`${this.apiUrl}/fridge`, ingredientsData).pipe(
      tap(response => {
        console.log('Ingredients added to fridge:', response);
      }),
      catchError(error => {
        console.error('Error adding ingredients to fridge:', error.message);
        return new Observable();
      })
    );
  }

  getFromFridge(): Observable<any> {
    console.log('Getting fridge content');
    return this.http.get<any>(`${this.apiUrl}/fridge`).pipe(
      tap(response => {
        console.log('Fridge content:', response);
      }),
      catchError(error => {
        console.error('Error getting fridge content:', error.message);
        return new Observable();
      })
    );
  }
}

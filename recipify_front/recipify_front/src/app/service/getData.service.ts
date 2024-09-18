import { Injectable, OnInit } from '@angular/core';
import { Observable } from "rxjs";
import { environment } from "../../environments/environment.development";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GetDataService implements OnInit {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  ngOnInit() {}

  // Fonction pour récupérer le token
  private getAuthToken(): string | null {
    return localStorage.getItem('authToken');
  }

  // Fonction pour créer les en-têtes avec le token
  private createHeaders(): HttpHeaders {
    const authToken = this.getAuthToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${authToken}`
    });
  }

  // Méthodes publiques qui ne nécessitent pas de token
  getAllergies(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/allergies`);
  }

  getDiets(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/diets`);
  }

  // Méthodes sécurisées nécessitant un token
  getIngredients(): Observable<any> {
    const headers = this.createHeaders();
    console.log('Headers being sent:', headers.get('Authorization'));
    return this.http.get<any>(`${this.apiUrl}/ingredients`, { headers });
  }

  getUnits(): Observable<any> {
    const headers = this.createHeaders();
    return this.http.get<any>(`${this.apiUrl}/units`, { headers });
  }

  getMyIngredients(): Observable<any> {
    const headers = this.createHeaders();
    return this.http.get<any>(`${this.apiUrl}/my-ingredients`, { headers });
  }

  // Requêtes de recettes : public ne nécessite pas de token, private et favorite en ont besoin
  getPublicRecipe(): Observable<any> {
    return this.http.get<any>(this.getRecipeUrl('public'));
  }

  getPrivateRecipe(): Observable<any> {
    const headers = this.createHeaders();
    return this.http.get<any>(this.getRecipeUrl('private'), { headers });
  }

  getFavoriteRecipe(): Observable<any> {
    const headers = this.createHeaders();
    return this.http.get<any>(this.getRecipeUrl('favorite'), { headers });
  }

  private getRecipeUrl(type: string): string {
    switch (type) {
      case 'public':
        return `${this.apiUrl}/public-recipes`;
      case 'private':
        return `${this.apiUrl}/private-recipes`;
      case 'favorite':
        return `${this.apiUrl}/favorite-recipes`;
      default:
        return `${this.apiUrl}/recipes`;
    }
  }
}

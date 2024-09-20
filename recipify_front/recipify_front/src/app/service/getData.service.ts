import {Injectable, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.development";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GetDataService implements OnInit {
  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
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
    return this.http.get<any>(`${this.apiUrl}/ingredients`);
  }

  getUnits(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/units`);
  }

  getMyIngredients(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/my-ingredients`);
  }


  getPublicRecipe(): Observable<any> {
    return this.http.get<any>(this.getRecipeUrl('public'));
  }

  getPrivateRecipe(): Observable<any> {
    return this.http.get<any>(this.getRecipeUrl('private'));
  }

  getFavoriteRecipe(): Observable<any> {
    return this.http.get<any>(this.getRecipeUrl('favorite'));
  }

  getAdminRecipe(): Observable<any> {
    return this.http.get<any>(this.getRecipeUrl('admin'));
  }
  getRecipeBySlug(slug: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/recipe/${slug}`);
  }


  private getRecipeUrl(type: string): string {
    switch (type) {
      case 'public':
        return `${this.apiUrl}/recipes`;
      case 'private':
        return `${this.apiUrl}/private-recipes`;
      case 'favorite':
        return `${this.apiUrl}/favorite`;
      case 'admin':
        return `${this.apiUrl}/admin`;
      default:
        return `${this.apiUrl}/recipes`;
    }
  }
}

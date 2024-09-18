import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Recette} from "../utils/types/recetteType";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RecetteService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getAllRecipes(): Observable<Recette[]>{
    return this.http.get<Recette[]>(this.apiUrl + 'recipes');
  }

  getRecipeBySlug(slug: string): Observable<Recette>{
    return this.http.get<Recette>(this.apiUrl + `recipe/${slug}`);
  }

  createRecipe(recipe: Recette): Observable<string>{
    return this.http.post<string>(this.apiUrl + 'recipe', recipe);
  }

  updateRecipe(slug: string, updatedRecipe: Recette): Observable<string> {
    return this.http.put<string>(`${this.apiUrl}/recipe/${slug}`, updatedRecipe);
  }
  deleteRecipe(slug: string): Observable<string> {
    return this.http.delete<string>(`${this.apiUrl}/recipe/${slug}`);
  }


}

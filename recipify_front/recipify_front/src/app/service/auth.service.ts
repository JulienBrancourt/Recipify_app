import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiUrl = environment.apiUrl

  constructor(private http: HttpClient) { }

  login(email: string , password: string ): Observable<any> {
    return this.http.post<{ token: string  }>(`${this.apiUrl}/authenticate`, {email, password})
      .pipe(
        tap(response => {
          localStorage.setItem('authToken', response.token);
        })
      )
      ;
  }
  register(email: string, password: string): Observable<any> {
    const payload = { email, password };
    return this.http.post(`${this.apiUrl}/register`, payload);
  }
  logout(): void {
    localStorage.removeItem('authToken');
  }

  isLoggedIn(): boolean {
    return!!localStorage.getItem('authToken');
  }
}

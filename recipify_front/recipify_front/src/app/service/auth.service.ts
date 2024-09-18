import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment.development";
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, catchError, Observable, tap, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  apiUrl = environment.apiUrl

  private loggedIn = new BehaviorSubject<boolean>(false);
  private adminRole = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {
    // Check if the user is already logged in when the service initializes
    const token = localStorage.getItem('authToken');
    if (token) {
      this.loggedIn.next(true);
      this.checkAdminRole(token);
    }
  }

  isLogginObservable(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  login(username: string, password: string): Observable<{ token: string }> {
    const requestBody = {
      username: username,
      password: password
    };

    return this.http.post<{ token: string }>(`${this.apiUrl}/authenticate`, requestBody)
      .pipe(
        tap(response => {
          // Save the token to localStorage
          localStorage.setItem('authToken', response.token);
          console.log('Login successful, JWT token stored.');
          // Update the loggedIn state to true
          this.loggedIn.next(true);
          this.checkAdminRole(response.token);
        }),
        catchError(error => {
          console.error('Login error:', error.message);
          return throwError(() => new Error('Login failed. Please try again later.'));
        })
      );
  }

  checkAdminRole(token: string): void {
    const decodedToken = this.decodeToken(token);
    console.log('Decoded Token:', decodedToken); // Ajoute cette ligne pour vérifier le contenu

    if (decodedToken && decodedToken.sub === 'admin') {
      console.log('User is admin');
      this.adminRole.next(true);
    } else {
      console.log('User is not admin');
      this.adminRole.next(false);
    }
  }


  decodeToken(token: string): any {
    try {
      // Sépare le token et extrait le payload
      const payload = token.split('.')[1];
      console.log('decoded Payload', atob((payload)))

      // Décode le payload en Base64 et le transforme en JSON
      return JSON.parse(atob(payload));

    } catch (e) {
      // En cas d'erreur, affiche un message et retourne null
      console.error('Error decoding token:', e);
      return null;
    }
  }


  register(username: string, password: string, allergies: String[], diets: String[] ): Observable<any> {
    const payload = { username, password, allergies, diets };
    return this.http.post(`${this.apiUrl}/register`, payload);
  }

  logout(): void {
    // Remove the token and update the loggedIn state
    localStorage.removeItem('authToken');
    this.loggedIn.next(false);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken');
  }

  isAdmin(): boolean {
    console.log('Is Admin:', this.adminRole.value)
    return this.adminRole.value; // retourne true si admin
  }


}

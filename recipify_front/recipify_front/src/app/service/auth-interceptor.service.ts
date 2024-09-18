import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {

  constructor() {
    console.log("AuthInterceptorService initialized");
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log(`Intercepting request to: ${req.url}`);
    // Ne pas ajouter le token pour les requêtes vers ces URLs publiques
    const publicUrls = ['/authenticate', '/register'];

    // Vérifier si l'URL est publique
    const isPublicUrl = publicUrls.some(url => req.url.includes(url));

    // Récupérer le token JWT depuis le localStorage
    const authToken = localStorage.getItem('authToken');

    // Cloner la requête pour ajouter le token d'autorisation si ce n'est pas une URL publique
    const authReq = (!isPublicUrl && authToken) ? req.clone({
      setHeaders: {
        Authorization: `Bearer ${authToken}`
      }
    }) : req;

    // Passer la requête (authentifiée ou non) à la chaîne suivante
    return next.handle(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // Gestion d'une requête non autorisée (401)
          console.error('Unauthorized request - possibly due to expired or invalid token');
          // Optionnel : redirection vers la page de login
          window.location.href = '/login';
        }
        // Retourner une nouvelle erreur
        return throwError(() => new Error(error.message));
      })
    );
  }
}

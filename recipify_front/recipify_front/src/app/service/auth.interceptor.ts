import {HttpInterceptorFn, HttpErrorResponse} from '@angular/common/http';
import {inject} from '@angular/core';
import {AuthService} from './auth.service';
import {Router} from '@angular/router';
import {catchError, throwError} from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthService);  // Injection du service AuthService
    const router = inject(Router);            // Injection du service Router pour redirection
    const authToken = authService.getToken();
    // Cloner la requête et ajouter le token si présent
    const authReq = authToken ? req.clone({setHeaders: {Authorization: `Bearer ${authToken}`}}) : req;

    return next(authReq).pipe(
        catchError((error: HttpErrorResponse) => {
            if (error.status === 401) {
                // Gestion du code 401: redirection vers la page de connexion
                console.error('Erreur 401: Non autorisé - Redirection vers la page de connexion');
                authService.logout();  // Déconnexion de l'utilisateur
                router.navigate(['/login']);  // Rediriger vers la page de connexion
            }

            if (error.status === 403) {
                // Gestion du code 403: accès interdit
                console.error('Erreur 403: Accès interdit - Redirection vers la page d\'erreur');
                router.navigate(['/access-denied']);  // Rediriger vers une page "Accès interdit" ou similaire
            }

            // Relancer l'erreur si ce n'est pas un code 401 ou 403
            return throwError(() => new Error(error.message));
        })
    );
};

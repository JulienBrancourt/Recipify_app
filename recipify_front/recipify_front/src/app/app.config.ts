import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, HTTP_INTERCEPTORS } from '@angular/common/http'; // Ajoutez HTTP_INTERCEPTORS
import { AuthInterceptorService } from './service/auth-interceptor.service'; // Import de votre intercepteur
import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    {
      provide: HTTP_INTERCEPTORS, // Fournir l'intercepteur HTTP
      useClass: AuthInterceptorService, // Utiliser votre service
      multi: true // Permet d'ajouter plusieurs intercepteurs si besoin
    }
  ]
};

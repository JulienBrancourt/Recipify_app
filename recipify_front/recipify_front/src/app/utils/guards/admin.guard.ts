import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "../../service/auth.service";

export const adminGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  if(!authService.isLoggedIn()) {
    router.navigate(['/login'])
    return false;
  }

  if (!authService.isAdmin()) {
    router.navigate(['/unauthorized']);
    return false;
  }

  return true;
};

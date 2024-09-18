import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { dashboardComponent } from './pages/dashboard/dashboard.component';
import { FavorisComponent } from './pages/favoris/favoris.component';
import { FormRecetteComponent } from './components/form-recette/form-recette.component';
import { RecetteComponent } from './pages/recette/recette.component';
import {RegisterComponent} from "./pages/register/register.component";
import {P404Component} from "./pages/p404/p404.component";
import {adminGuard} from "./utils/guards/admin.guard";
import {P403Component} from "./pages/p403/p403.component";



export const routes: Routes =

[{path: '', component: LoginComponent},

 {path: 'dashboard', component: dashboardComponent},

 {path: 'favoris', component: FavorisComponent},

 {path: 'form-recette', component: FormRecetteComponent},

 {path: 'recette', component: RecetteComponent},

  {path: 'admin', loadComponent: () => import('./pages/admin/admin.component').then(module => module.AdminComponent), canActivate: [adminGuard]},

  {path: 'register', component: RegisterComponent},

  { path: 'unauthorized', component: P403Component },

  {path: '**', component: P404Component}

];

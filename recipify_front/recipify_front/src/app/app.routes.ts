import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { dashboardComponent } from './pages/dashboard/dashboard.component';
import { FavorisComponent } from './pages/favoris/favoris.component';
import { FormRecetteComponent } from './components/form-recette/form-recette.component';
import { RecetteComponent } from './pages/recette/recette.component';
import {AdminComponent} from "./pages/admin/admin.component";
import {RegisterComponent} from "./pages/register/register.component";
import {P404Component} from "./pages/p404/p404.component";



export const routes: Routes =

[{path: '', component: LoginComponent},

 {path: 'dashboard', component: dashboardComponent},

 {path: 'favoris', component: FavorisComponent},

 {path: 'form-recette', component: FormRecetteComponent},

 {path: 'recette', component: RecetteComponent},

  {path: 'admin', component: AdminComponent},

  {path: 'register', component: RegisterComponent},

  {path: '**', component: P404Component}

];

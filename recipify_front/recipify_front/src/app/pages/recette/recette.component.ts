import { Component } from '@angular/core';
import {RecetteCardComponent} from "../../components/recette-card/recette-card.component";
import {Recette} from "../../utils/types/recetteType";
import {Router} from "@angular/router";

@Component({
  selector: 'app-recette',
  standalone: true,
  imports: [
    RecetteCardComponent
  ],
  templateUrl: './recette.component.html',
  styleUrl: './recette.component.css'
})
export class RecetteComponent {
  recettes: Recette[] = [];
  constructor() {}

}

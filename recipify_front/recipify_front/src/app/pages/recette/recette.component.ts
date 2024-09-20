import {Component, OnInit} from '@angular/core';
import {RecetteCardComponent} from "../../components/recette-card/recette-card.component";
import {Recette} from "../../utils/types/recetteType";
import {Router} from "@angular/router";
import {GetDataService} from "../../service/getData.service";

@Component({
  selector: 'app-recette',
  standalone: true,
  imports: [
    RecetteCardComponent
  ],
  templateUrl: './recette.component.html',
  styleUrl: './recette.component.css'
})
export class RecetteComponent implements OnInit {

  recettes: Recette[] = [];
  constructor(private data: GetDataService) {}

  ngOnInit() {
    this.data.getPublicRecipe().subscribe({
      next: (recettes) => {
        this.recettes = recettes;
        console.log('Recettes:', recettes);
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des recettes:', err);
      }
    });
  }
}

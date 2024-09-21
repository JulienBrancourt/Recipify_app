import {Component, OnInit} from '@angular/core';

import {RouterLink} from "@angular/router";
import {GetDataService} from "../../service/getData.service";
import {RecetteCardComponent} from "../../components/recette-card/recette-card.component";
import {Recette} from "../../utils/types/recetteType";
import {NgForOf} from "@angular/common";


@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [RouterLink, RecetteCardComponent, NgForOf],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit{
  recettes: Recette[] = [
  ];

  constructor(private data: GetDataService) { }

  ngOnInit() {
    this.data.getAdminRecipe().subscribe({
      next: (recettes) => {
        this.recettes = recettes;
        console.log('admin Recettes:', this.recettes);
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des recettes:', err);
      }
    });
  }
  onRecetteDeleted(slug: string) {
    this.recettes = this.recettes.filter(recette => recette.slug !== slug);
    console.log(`Recette avec le slug "${slug}" supprimée de la liste.`);
  }


}

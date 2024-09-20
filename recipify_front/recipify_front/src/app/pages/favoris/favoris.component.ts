import { Component, OnInit } from '@angular/core';
import { Recette } from "../../utils/types/recetteType";
import { FavorisService } from "../../service/favoris.service";
import { RecetteCardComponent } from "../../components/recette-card/recette-card.component";
import { GetDataService } from "../../service/getData.service";
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-favoris',
  standalone: true,
  imports: [
    RecetteCardComponent
  ],
  templateUrl: './favoris.component.html',
  styleUrls: ['./favoris.component.css']
})
export class FavorisComponent implements OnInit {
  favoris: Recette[] = [];


  constructor(
    private favorisService: FavorisService,
    private getDataService: GetDataService,
  ) {}

  ngOnInit(): void {
  this.getUserFavoriteRecipes();
  }

  getUserFavoriteRecipes(): void {
    this.getDataService.getFavoriteRecipe().subscribe({
      next: (favoris) => {
        this.favoris = favoris;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des favoris:', err);
      }
    });
  }

  onFavoriChanged() {
    this.favorisService.loadFavoris();
  }

  removeFromFavorites(title: string) {
    this.favorisService.removeFavori(title).subscribe(() => {
      this.getUserFavoriteRecipes();
    });
  }
}

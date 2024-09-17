import { Component } from '@angular/core';
import {Recette} from "../../utils/types/recetteType";
import {FavorisService} from "../../service/favoris.service";
import {RecetteCardComponent} from "../../components/recette-card/recette-card.component";

@Component({
  selector: 'app-favoris',
  standalone: true,
  imports: [
    RecetteCardComponent
  ],
  templateUrl: './favoris.component.html',
  styleUrl: './favoris.component.css'
})
export class FavorisComponent {
favoris: Recette[] = [];

constructor(private favorisService: FavorisService) {
  this.favoris = this.favorisService.getFavoris();
}

removeFromFavorites(title: string) {
  this.favorisService.removeFavori(title);
  this.favoris = this.favorisService.getFavoris();
}
}

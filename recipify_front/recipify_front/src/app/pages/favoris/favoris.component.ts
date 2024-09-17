import { Component, OnInit } from '@angular/core';
import { Recette } from "../../utils/types/recetteType";
import { FavorisService } from "../../service/favoris.service";
import {RecetteCardComponent} from "../../components/recette-card/recette-card.component";

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

  constructor(private favorisService: FavorisService) {}

  ngOnInit() {
    this.favorisService.favoris$.subscribe(favoris => {
      this.favoris = favoris;
    });
  }


  onFavoriChanged() {
    this.favorisService.loadFavoris();
  }


  removeFromFavorites(title: string) {
    this.favorisService.removeFavori(title);
  }
}

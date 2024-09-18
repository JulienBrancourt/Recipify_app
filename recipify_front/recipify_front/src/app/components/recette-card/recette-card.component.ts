import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Recette } from "../../utils/types/recetteType";
import { FavorisService } from "../../service/favoris.service";

@Component({
  selector: 'app-recette-card',
  standalone: true,
  imports: [],
  templateUrl: './recette-card.component.html',
  styleUrls: ['./recette-card.component.css']
})
export class RecetteCardComponent {
  @Input() recette!: Recette;
  @Input() view: 'global' | 'favorite' = 'global';

  @Output() favoriChanged = new EventEmitter<void>();

  constructor(private favorisService: FavorisService) {}

  addToFavorite() {
    if (this.favorisService.isInFavoris(this.recette)) {
      alert('Cette recette est déjà dans vos favoris');
    } else {
      this.favorisService.addFavori(this.recette);
      alert('Recette ajoutée aux favoris');
      this.favoriChanged.emit();
    }
  }

  removeFromFavorites(title: string) {
    this.favorisService.removeFavori(title);
    alert('Recette supprimée des favoris');
    this.favoriChanged.emit();
  }
}

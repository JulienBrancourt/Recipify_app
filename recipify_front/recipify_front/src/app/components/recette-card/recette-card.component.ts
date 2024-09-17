import {Component, Input} from '@angular/core';
import {Recette} from "../../utils/types/recetteType";
import {FavorisService} from "../../service/favoris.service";


@Component({
  selector: 'app-recette-card',
  standalone: true,
  imports: [],
  templateUrl: './recette-card.component.html',
  styleUrl: './recette-card.component.css'
})
export class RecetteCardComponent {
  @Input() recette!: Recette;

  constructor(private favoriseService: FavorisService) {
  }

  addToFavorite() {
    const isExisting = this.favoriseService.isInFavoris(this.recette);

    if (isExisting) {
      alert('Cette recette est déjà dans vos favoris');
    } else {
      this.favoriseService.addFavori(this.recette);
      alert('Recette ajoutée aux favoris');
    }
  }

}

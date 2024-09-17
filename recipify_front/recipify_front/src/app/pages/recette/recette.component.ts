import { Component } from '@angular/core';
import {RecetteCardComponent} from "../../components/recette-card/recette-card.component";
import {Recette} from "../../utils/types/recetteType";

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
  recettes: Recette[] = [
    {
      title: 'Poutine',
      instruction: "Découpez les pommes de terre et les éplucher. Faites chauffer 2 cuisines à 180°C (100°C). Étalez une couche de pâte feuilletée et ajoutez 2 cubes de sucre et 2 cubes de beurre. Faites fondre à feu vif jusqu'à ce que la pâte soit dure et bouillante. Passez le pâte dans une poêle et ajoutez les pommes de terre. Faites cuire 10 à 15 minutes, ou jusqu'à ce que la pâte soit bien dorée. Ajoutez le beurre et la farine. Faites bouillir une pâ",
      calorie: 0,
      serving: 0,
      ingredients: []
    }
  ];

}

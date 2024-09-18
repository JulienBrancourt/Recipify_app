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
      calorie: 10,
      serving: 1,
      ingredients: [
        { ingredientName: 'Pommes de terre', calorie: 100 },
        { ingredientName: 'Sucre', calorie: 100 },
        { ingredientName: 'Beurre', calorie: 100 },
        { ingredientName: 'Farine', calorie: 100 }
      ]
    },
    {
      title: 'Tarte aux pommes',
      instruction: "Dans un moule, mélangez les pommes de terre, le sucre, le beurre, le lait, le sel et le coulis de poivre. Épaissez la pâte feuilletée et ajoutez le mélange. Faites fondre à feu vif jusqu'à ce que la pâ",
      calorie: 0,
      serving: 1,
      ingredients: [
        { ingredientName: 'Pommes de terre', calorie: 100 },
        { ingredientName: 'Sucre', calorie: 100 },
        { ingredientName: 'Beurre', calorie: 100 },
        { ingredientName: 'Lait', calorie: 100 },
        { ingredientName: 'Sel', calorie: 100 },
        { ingredientName: 'Coulis de poivre', calorie: 100 }
      ]
    },
    {
      title: 'Tarte aux fraises',
      instruction: "Dans un moule, mélangez les pommes de terre, le sucre, le beurre, le lait, le sel et le coulis de poivre. Épaissez la pâte feuilletée et ajoutez le mélange. Faites fondre à feu vif jusqu'à ce que la pâ",
      calorie: 0,
      serving: 1,
      ingredients: [
        { ingredientName: 'Pommes de terre', calorie: 100 },
        { ingredientName: 'Sucre', calorie: 100 },
        { ingredientName: 'Beurre', calorie: 100 },
        { ingredientName: 'Lait', calorie: 100 },
        { ingredientName: 'Sel', calorie: 100 },
        { ingredientName: 'Coulis de poivre', calorie: 100 }
      ]
    },
    {
      title: 'Tarte aux maroille',
      instruction: "Dans un moule, mélangez les pommes de terre, le sucre, le beurre, le lait, le sel et le coulis de poivre. Épaissez la pâte feuilletée et ajoutez le mélange. Faites fondre à feu vif jusqu'à ce que la pâ",
      calorie: 0,
      serving: 1,
      ingredients: [
        { ingredientName: 'Pommes de terre', calorie: 100 },
        { ingredientName: 'Sucre', calorie: 100 },
        { ingredientName: 'Beurre', calorie: 100 },
        { ingredientName: 'Lait', calorie: 100 },
        { ingredientName: 'Sel', calorie: 100 },
        { ingredientName: 'Coulis de poivre', calorie: 100 }
      ]
    }
  ];

}

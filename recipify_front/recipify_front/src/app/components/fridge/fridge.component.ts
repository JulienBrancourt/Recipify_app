import {Component, OnInit} from '@angular/core';
import {FridgeService} from "../../service/fridge.service";

@Component({
  selector: 'app-fridge',
  standalone: true,
  imports: [],
  templateUrl: './fridge.component.html',
  styleUrl: './fridge.component.css'
})
export class FridgeComponent implements OnInit {
  constructor(private fridgService: FridgeService) {}
  fridgeIngredients: string[] = [];

  ngOnInit() {
    this.fridgService.getFromFridge().subscribe({
        next: (fridgeIngredients) => {
          console.log('Fridge content:', fridgeIngredients);
        },
        error: (error) => {
          console.error('Error getting fridge content:', error.message);
        },
        complete: () => {
          console.log('Fridge content retrieved');
        }
      }
    );
  }
  // Traitement des données / pipe classement par ordre alphabétique / Catégories
}

import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FridgeService} from "../../service/fridge.service";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {UpdateService} from "../../service/behaviour-subject.service";

@Component({
  selector: 'app-fridge',
  standalone: true,
  imports: [
    DatePipe,
    NgForOf,
    NgIf
  ],
  templateUrl: './fridge.component.html',
  styleUrl: './fridge.component.css'
})
export class FridgeComponent implements OnInit {
  // constructor(private fridgService: FridgeService) {}
  // fridgeIngredients: string[] = [];
  //
  //
  // ngOnInit() {
  //   this.fridgService.getFromFridge().subscribe({
  //       next: (fridgeIngredients) => {
  //         console.log('Fridge content:', fridgeIngredients);
  //       },
  //       error: (error) => {
  //         console.error('Error getting fridge content:', error.message);
  //       },
  //       complete: () => {
  //         console.log('Fridge content retrieved');
  //       }
  //     }
  //   );
  // }
  // Traitement des données / pipe classement par ordre alphabétique / Catégories


  // Déclare un tableau d'objets avec les propriétés attendues
  fridgeIngredients: { unit: string; quantity: number; name: string; expiration: Date }[] = [];

  constructor(private fridgService: FridgeService, private updateService: UpdateService) {
  }

  ngOnInit() {
    this.loadFridgeContents();
    this.updateService.update$.subscribe(() => {
      this.loadFridgeContents(); // Recharge les ingrédients quand update est déclenché
    });
  }

  private loadFridgeContents() {
    this.fridgService.getFromFridge().subscribe({
      next: (ingredients) => {
        this.fridgeIngredients = ingredients;
        console.log('Fridge content:', this.fridgeIngredients);
      },
      error: (error) => {
        console.error('Error getting fridge content:', error.message);
      },
      complete: () => {
        console.log('Fridge content retrieved');
      }
    });
  }
}




import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FridgeService} from "../../service/fridge.service";
import {DatePipe, LowerCasePipe, NgForOf, NgIf, TitleCasePipe} from "@angular/common";
import {UpdateService} from "../../service/behaviour-subject.service";
import {SortPipe} from "../../pipe/sort.pipe";

@Component({
  selector: 'app-fridge',
  standalone: true,
  imports: [
    DatePipe,
    NgForOf,
    NgIf,
    LowerCasePipe,
    TitleCasePipe,
    SortPipe
  ],
  templateUrl: './fridge.component.html',
  styleUrl: './fridge.component.css',
})
export class FridgeComponent implements OnInit {
  // Déclare un tableau d'objets avec les propriétés attendues
  fridgeIngredients: { unit: string; quantity: number; name: string; expiration: Date }[] = [];
  sortColumn: string = 'name';
  sortDirection: 'asc' | 'desc' = 'asc';

  constructor(private fridgService: FridgeService, private updateService: UpdateService) {
  }

  ngOnInit() {
    this.loadFridgeContents();
    this.updateService.update$.subscribe(() => {
      this.loadFridgeContents();
    });
  }

  private loadFridgeContents() {
    this.fridgService.getFromFridge().subscribe({
      next: (ingredients) => {
        this.fridgeIngredients = ingredients;
        console.log('Fridge content:', this.fridgeIngredients);
        // Log des Unités
        console.log('Units:', this.fridgeIngredients.map((i) => i.unit));
      },
      error: (error) => {
        console.error('Error getting fridge content:', error.message);
      },
      complete: () => {
        console.log('Fridge content retrieved');
      }
    });
  }

  changeSort(column: string) {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }
  }
}




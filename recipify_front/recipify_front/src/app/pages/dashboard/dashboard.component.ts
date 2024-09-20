import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder,FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {NgForOf, NgIf} from "@angular/common";
import {GetDataService} from "../../service/getData.service";
import {FridgeService} from "../../service/fridge.service";
import {UnitService} from "../../service/unit-service.service";
import {FridgeComponent} from "../../components/fridge/fridge.component";
import {UpdateService} from "../../service/behaviour-subject.service";


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgIf,
    FridgeComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']  // Correction ici, c'est "styleUrls" au pluriel
})
export class dashboardComponent implements OnInit {
  ingredientForm: FormGroup;
  ingredientsArray: string[] = [];
  units: string[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private dataService: GetDataService,
    private fridgeService: FridgeService,

    private unitService: UnitService,

    private updateService: UpdateService,

  ) {
    this.ingredientForm = this.fb.group({
      ingredients: this.fb.array([]),
    });
  }


  ngOnInit(): void {
    this.addInput(); // Ajoute un champ par défaut au démarrage

    // Récupération des ingrédients
    this.dataService.getIngredients().subscribe({
      next: (ingredientsArray) => {
        this.ingredientsArray = ingredientsArray.map((i: any) => i.ingredientName);
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des ingrédients:', err);
      }
    });

    // Récupération des unités
    this.dataService.getUnits().subscribe({
      next: (units) => {
        this.units = units;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des unités:', err);
      }
    });
  }

  get ingredients(): FormArray {
    return this.ingredientForm.get('ingredients') as FormArray;
  }

  getUnitDisplayName(unit: string): string {
    return this.unitService.getDisplayName(unit);
  }

  addInput(): void {
    const ingredientGroup = this.fb.group({
      name: [''],
      quantity: ['', [Validators.required, Validators.min(1)]],
      unit: [''],
      expiration: ['']
    });
    this.ingredients.push(ingredientGroup);
  }


  handleSubmit() {
    if (this.ingredientForm.valid) {
      // Filtrer les ingrédients avec des champs non vides
      const formIngredientData = this.ingredientForm.value.ingredients.filter((ingredient: any) => {
        return ingredient.name && ingredient.quantity && ingredient.unit && ingredient.expiration;
      });

      if (formIngredientData.length === 0) {
        // Afficher un message d'erreur si tous les ingrédients sont vides
        console.error('Aucun ingrédient valide à ajouter');
        return;
      }

      console.log('Ingrédients ajoutés au frigo:', formIngredientData);
      this.fridgeService.addToFridge(formIngredientData).subscribe({
        next: (response) => {
          console.log('Ingrédients ajoutés au frigo avec succès:', response);
          this.updateService.triggerUpdate()
        },
        error: (err) => {
          console.error('Erreur lors de l\'ajout des ingrédients au frigo:', err);
        }
      });
    } else {
      console.error('Formulaire invalide');
    }
  }

  goToPage() {
    this.router.navigate(['/recipe']);
  }

}

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UnitService {

  private unitDisplayMap: { [key: string]: string } = {
    'GRAMMES': 'g',
    'KILOGRAMMES': 'kg',
    'LITRES': 'L',
    'CENTILITRES': 'cl',
    'MILLILITRES': 'ml',
    'CUILLERES_A_SOUPE': 'cuillères à soupe',
    'CUILLERES_A_CAFE': 'cuillères à café',
    'TASSE': 'tasse',
    'VERRE': 'verre',
    'PINCEE': 'pincée',
    'GOUTTE': 'goutte'
  };

  // Méthode pour obtenir la version affichée de l'unité
  getDisplayName(value: string): string {
    const displayValue = this.unitDisplayMap[value];
    if (!displayValue) {
      console.warn('No display value found for:', value); // Alerte si l'unité n'est pas trouvée
    }
    return displayValue || value;
  }

}

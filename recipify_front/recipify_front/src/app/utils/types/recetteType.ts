export type Recette = {
  title: string;
  instruction: string;
  calorie: number;
  serving: number;
  ingredients: Ingredient[];
}

export type Ingredient = {
  ingredientName: string;
  calorie: number;
}

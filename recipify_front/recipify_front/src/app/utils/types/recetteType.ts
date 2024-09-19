export type Recette = {
  title: string;
  slug: string;
  instruction: string;
  calorie: number;
  serving: number;
  ingredients: Ingredient[];
}

export type Ingredient = {
  ingredientName: string;
  calorie: number;
  quantity: number;
  unit: string;
}

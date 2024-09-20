// Typescript modifications
export type Recette = {
  title: string;
  slug: string;
  instruction: string;
  calorie: number;
  serving: number;
  recipeIngredients: RecipeIngredient[];  // Changed to RecipeIngredient[]
  public: boolean;
  time: number;
}

export type RecipeIngredient = {
  ingredient: Ingredient;
  quantity: number;
  unit: string;  // Assuming this refers to UnitOfMeasurement
}

export type Ingredient = {
  ingredientName: string;
  calorie: number;
}


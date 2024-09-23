CREATE SCHEMA "recipify";

INSERT INTO recipify.allergy (id, allergy_name) VALUES
    (1, 'gluten'),
    (2, 'lactose'),
    (3, 'arachides'),
    (4, 'fruits_a_coques'),
    (5, 'oeufs'),
    (6, 'poisson'),
    (7, 'soja'),
    (8, 'crustaces'),
    (9, 'moutarde');



INSERT INTO recipify.diet (id, diet_name) VALUES
    (1, 'vegetarien'),
    (2, 'vegan'),
    (3, 'sans_gluten'),
    (4, 'sans_lactose'),
    (5, 'paleo'),
    (6, 'keto'),
    (7, 'halal'),
    (8, 'kasher'),
    (9, 'pescetarien'),
    (10, 'flexitarien'),
    (11, 'crudivore'),
    (12, 'sans_sucre'),
    (13, 'fruitarien');


INSERT INTO recipify.ingredient (id, calorie, ingredient_category, ingredient_name) VALUES
    (1, 165, 'VIANDE', 'poulet'),
    (2, 208, 'FRUITS_DE_MER_ET_POISSONS', 'saumon'),
    (3, 41, 'LEGUME', 'carotte'),
    (4, 52, 'FRUIT', 'pomme'),
    (5, 402, 'PRODUIT_LAITIER', 'fromage'),
    (6, 130, 'CEREALE', 'riz'),
    (7, 251, 'EPICE_ET_AROMATE', 'poivre'),
    (8, 884, 'HUILE_ET_GRAISSE', 'huile d''olive'),
    (9, 112, 'SAUCE_ET_CONDIMENT', 'ketchup'),
    (10, 387, 'SUCRE_ET_EDULCORANT', 'sucre'),
    (11, 579, 'FRUIT_A_COQUE_ET_GRAINE', 'amande'),
    (12, 116, 'LEGUMINEUSE', 'lentille'),
    (13, 2, 'BOISSON', 'café'),
    (14, 85, 'ALCOOL', 'vin rouge'),
    (15, 59, 'PRODUIT_FERMENTE', 'yaourt'),
    (16, 266, 'PRODUIT_TRANSFORME', 'pizza surgelée'),
    (17, 364, 'FARINE_ET_AMIDON', 'farine de blé'),
    (18, 55, 'ADDITIF_ET_AGENT_DE_LEVEE', 'levure chimique'),
    (19, 76, 'SUBSTITUT_ALIMENTAIRE', 'tofu'),
    (20, 546, 'AUTRE', 'chocolat noir'),
    (21, 18, 'LEGUME', 'tomate'),
    (22, 250, 'VIANDE', 'boeuf'),
    (23, 50, 'FRUIT', 'ananas'),
    (24, 42, 'PRODUIT_LAITIER', 'lait'),
    (25, 304, 'SUCRE_ET_EDULCORANT', 'miel'),
    (26, 717, 'HUILE_ET_GRAISSE', 'beurre'),
    (27, 155, 'VIANDE', 'oeuf'),
    (28, 99, 'FRUITS_DE_MER_ET_POISSONS', 'crevette'),
    (29, 127, 'LEGUMINEUSE', 'haricot rouge'),
    (30, 567, 'FRUIT_A_COQUE_ET_GRAINE', 'cacahuète');
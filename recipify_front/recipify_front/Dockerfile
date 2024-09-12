# Utiliser une image Node.js pour le build
FROM node:18 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers package.json et package-lock.json
COPY package*.json ./

# Installer les dépendances
RUN npm install

# Copier le reste des fichiers de l'application
COPY . .

# Construire l'application Angular
RUN npm run build

# Utiliser une image Nginx pour servir l'application
FROM nginx:alpine

# Copier les fichiers construits dans Nginx
COPY --from=build /app/dist/recipify_front /usr/share/nginx/html

# Exposer le port 80
EXPOSE 80

# Lancer Nginx
CMD ["nginx", "-g", "daemon off;"]

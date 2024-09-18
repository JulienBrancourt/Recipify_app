import {Component, OnInit} from '@angular/core';
import {Recette} from "../../utils/types/recetteType";
import {ActivatedRoute} from "@angular/router";
import {RecetteComponent} from "../recette/recette.component";
import {GetDataService} from "../../service/getData.service";

@Component({
  selector: 'app-recette-details',
  standalone: true,
  imports: [],
  templateUrl: './recette-details.component.html',
  styleUrl: './recette-details.component.css'
})
export class RecetteDetailsComponent {
  // recette!: Recette | undefined;
  // isLoading = true;
  // errorMessage: string | null = null;
  //
  // constructor(private route: ActivatedRoute, private getDataService: GetDataService, private recetteComponent: RecetteComponent) {
  //   this.recetteComponent = recetteComponent
  // }
  //
  // ngOnInit():void {
  //   const slug = this.route.snapshot.paramMap.get('slug')!;
  //   this.recette = this.recetteComponent.getRecetteBySlug(slug);
  //   if (this.recette) {
  //     this.isLoading = false;
  //   } else {
  //     this.errorMessage = 'Recette non trouvée';
  //     this.isLoading = false;
  //   }
  // }
}

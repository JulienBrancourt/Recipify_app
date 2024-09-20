import {Component, OnInit} from '@angular/core';
import {Recette} from "../../utils/types/recetteType";
import {ActivatedRoute} from "@angular/router";
import {RecetteComponent} from "../recette/recette.component";
import {GetDataService} from "../../service/getData.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-recette-details',
  standalone: true,
  imports: [],
  templateUrl: './recette-details.component.html',
  styleUrl: './recette-details.component.css'
})
export class RecetteDetailsComponent {
  recette!: Recette | undefined;


  constructor(private route: ActivatedRoute, private getDataService: GetDataService) {
  }

  ngOnInit():void {
    const slug = this.route.snapshot.paramMap.get('slug')!;

  }
}

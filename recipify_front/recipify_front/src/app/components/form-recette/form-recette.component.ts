import {Component, OnInit} from '@angular/core';

import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClient} from "@angular/common/http";



@Component({
  selector: 'app-form-recette',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './form-recette.component.html',
  styleUrl: './form-recette.component.css'
})

export class FormRecetteComponent{


}



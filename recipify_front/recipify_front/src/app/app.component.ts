import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import {FooterComponent} from "./components/footer/footer.component";
import {NgIf} from "@angular/common";
import {LoadingCardComponent} from "./components/loading-card/loading-card.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavComponent, FooterComponent, NgIf, LoadingCardComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'recipify_front';

  // TODO gestion des observables pour la connexion
  isLogged: boolean = false;

  constructor() {
  }

  ngOnInit(): void {
    // Je m'abonne a l'observable de connexion qui est dans le service de Login pour check si j'ai bien mon Token
  }
}

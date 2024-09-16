import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import {FooterComponent} from "./components/footer/footer.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'recipify_front';

  // On vas placer un observable sur le statut de connexion de l'utilisateur pour masquer la barre de navigation si il n'est pas connecté
  constructor() {
  }

  ngOnInit(): void {
  }
}

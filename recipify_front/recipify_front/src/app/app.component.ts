import {Component, OnInit} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import { FooterComponent } from './components/footer/footer.component';
import { NgIf } from '@angular/common';
import { LoadingCardComponent } from './components/loading-card/loading-card.component';
import { AuthService } from './service/auth.service';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavComponent, FooterComponent, NgIf, LoadingCardComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'recipify_front';
  isLogged: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.isLogginObservable().subscribe((isLogged) => {
      this.isLogged = isLogged;
    });
  }
}

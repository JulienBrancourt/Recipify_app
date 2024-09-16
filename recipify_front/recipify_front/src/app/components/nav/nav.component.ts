import { Component, ElementRef, signal, ViewChild, AfterViewInit } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'] // Fix: styleUrl -> styleUrls
})
export class NavComponent implements AfterViewInit {
  @ViewChild('burger') burger!: ElementRef;
  @ViewChild('navLinks') navLinks!: ElementRef;

  constructor(private authService: AuthService, private router: Router) {
  }
  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
  // Signal to manage the state of the burger menu (open/close)
  isNavActive = signal(false);

  ngAfterViewInit() {}

  activeBurger() {
    const navLinks = this.navLinks.nativeElement;
    const burger = this.burger.nativeElement;

    // Toggle the active classes on burger and navLinks
    navLinks.classList.toggle('nav-active');
    burger.classList.toggle('toggle');
  }
}

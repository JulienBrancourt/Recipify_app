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

  // activeBurger() {  this.isNavActive.set(!this.isNavActive());
  // }

  activeBurger() {
    this.isNavActive.set(!this.isNavActive());
    if (this.isNavActive()) {
      this.navLinks.nativeElement.classList.add('nav-active');
    } else {
      this.navLinks.nativeElement.classList.remove('nav-active');
    }
  }
}

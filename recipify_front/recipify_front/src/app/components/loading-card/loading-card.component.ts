import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-loading-card',
  standalone: true,
  templateUrl: './loading-card.component.html',
  styleUrls: ['./loading-card.component.css']
})
export class LoadingCardComponent {
  @Input() isLoading: boolean = false;
}

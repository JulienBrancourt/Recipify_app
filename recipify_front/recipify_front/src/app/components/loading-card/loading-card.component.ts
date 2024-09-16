import { Component, Input } from '@angular/core';
import { NgIf } from '@angular/common'; // Import NgIf directive

@Component({
  selector: 'app-loading-card',
  standalone: true,
  imports: [NgIf], // Add NgIf to the imports array
  templateUrl: './loading-card.component.html',
  styleUrls: ['./loading-card.component.css']
})
export class LoadingCardComponent {
  @Input() isLoading: boolean = false;
}

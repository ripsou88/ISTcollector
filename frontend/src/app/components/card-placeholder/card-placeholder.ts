import { Component, Input } from '@angular/core';
import { Ist } from '../../interface/ist';

@Component({
  selector: 'app-card-placeholder',
  standalone: true,
  templateUrl: './card-placeholder.html',
  styleUrl: './card-placeholder.css',
})
export class CardPlaceholder {
  @Input() ist!: Ist;
}
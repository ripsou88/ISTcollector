import { Component, input } from '@angular/core';
import { Ist } from '../../interface/ist';
import { CardPopup } from '../card-popup/card-popup';

@Component({
  selector: 'app-card-placeholder',
  imports: [CardPopup],
  templateUrl: './card-placeholder.html',
  styleUrl: './card-placeholder.css',
})
export class CardPlaceholder {
  ist = input.required<Ist>();
}

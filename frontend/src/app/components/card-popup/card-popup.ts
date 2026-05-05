import { Component, input } from '@angular/core';

import { Ist } from '../../interface/ist';
@Component({
  selector: 'app-card-popup',
  imports: [],
  templateUrl: './card-popup.html',
  styleUrl: './card-popup.css',
})
export class CardPopup {
  readonly ist = input.required<Ist>();
}

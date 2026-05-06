import { Component, input, output } from '@angular/core';
import { TransmissionEmoji } from '../../enum/transmission';

import { Ist } from '../../interface/ist';
import { Transmission } from '../../enum/transmission';
import { TypeIstEmoji } from '../../enum/type-ist';
@Component({
  selector: 'app-card-popup',
  imports: [],
  templateUrl: './card-popup.html',
  styleUrl: './card-popup.css',
})
export class CardPopup {
  readonly ist = input.required<Ist>();
  readonly close = output<void>();

  //TODO: also add type ist
  protected typeIstEmoji = TypeIstEmoji;
  protected transmissionEmoji = TransmissionEmoji;
}

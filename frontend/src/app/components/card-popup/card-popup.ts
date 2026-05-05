import { Component, input, output } from '@angular/core';

import { Ist } from '../../interface/ist';
import { Transmission } from '../../enum/transmission';
@Component({
  selector: 'app-card-popup',
  imports: [],
  templateUrl: './card-popup.html',
  styleUrl: './card-popup.css',
})
export class CardPopup {
  readonly ist = input.required<Ist>();
  readonly close = output<void>();

  //TODO: change to transmissionEmoji
  //TODO: also add type ist
  protected transmissionLabel(transmission: Transmission): string {
    switch (transmission) {
      case Transmission.Contact_Sanguin:
        return 'Contact Sanguin';
      case Transmission.Contact_Direct:
        return 'Contact Direct';
      case Transmission.Materno_Foetale:
        return 'Materno Foetale';
      case Transmission.Orale:
        return 'Orale';
      case Transmission.Sexuelle:
        return 'Sexuelle';
    }
  }
}

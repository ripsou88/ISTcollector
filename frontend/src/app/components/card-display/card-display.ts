import { Component, input, OnChanges, signal } from '@angular/core';
import { CardPlaceholder } from '../card-placeholder/card-placeholder';
import { Ist } from '../../interface/ist';
import { CardPopup } from '../card-popup/card-popup';

@Component({
  selector: 'app-card-display',
  imports: [CardPlaceholder, CardPopup],
  templateUrl: './card-display.html',
  styleUrl: './card-display.css',
})
export class CardDisplay implements OnChanges {
  public readonly ist = input.required<Ist>();
  public readonly forceOpen = input<boolean>(false);
  public readonly count = input<number>(0);

  protected readonly isModalOpen = signal(false);
  protected readonly isAnimating = signal(false);

  ngOnChanges() {
    if (this.forceOpen()) {
      this.isModalOpen.set(true);
    }
  }

  protected openModal(): void {
    if (this.isAnimating() || this.isModalOpen()) {
      return;
    }

    this.isAnimating.set(true);

    setTimeout(() => {
      this.isModalOpen.set(true);
      this.isAnimating.set(false);
    }, 400);
  }

  protected closeModal(): void {
    this.isModalOpen.set(false);
  }
}

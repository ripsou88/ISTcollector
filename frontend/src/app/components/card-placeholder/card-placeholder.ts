import { Component, input, signal } from '@angular/core';
import { Ist } from '../../interface/ist';
import { CardPopup } from '../card-popup/card-popup';

@Component({
  selector: 'app-card-placeholder',
  imports: [CardPopup],
  templateUrl: './card-placeholder.html',
  styleUrl: './card-placeholder.css',
})
export class CardPlaceholder {
  public readonly ist = input.required<Ist>();

  protected readonly isModalOpen = signal(false);
  protected readonly isAnimating = signal(false);

  protected openModal(): void {
    if (this.isAnimating() || this.isModalOpen()) {
      return;
    }

    this.isAnimating.set(true);

    setTimeout(() => {
      this.isModalOpen.set(true);
      this.isAnimating.set(false);
    }, 300);
  }

  protected closeModal(): void {
    this.isModalOpen.set(false);
  }
}

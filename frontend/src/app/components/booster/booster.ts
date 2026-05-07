import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CardsService } from '../../service/cards-service';
import { Ist } from '../../interface/ist';
import { CardPlaceholder } from '../card-placeholder/card-placeholder';
import { QuizzService } from '../../service/quizz-service';

@Component({
  selector: 'app-booster',
  imports: [CommonModule, CardPlaceholder],
  templateUrl: './booster.html',
  styleUrl: './booster.css',
})
export class Booster {
  protected isOpen: boolean = false;
  protected showCards: boolean = false;
  protected boosterCards: Ist[] = [];

  private cards: CardsService = inject(CardsService);
  private quizzService: QuizzService = inject(QuizzService);
  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  boosterOuverture() {
    console.log('boosterOuverture appelée');
    this.isOpen = true;

    this.cards.getThreeRandom().subscribe((cards) => {
      this.boosterCards = cards;
      this.cdr.detectChanges();
    });

    this.quizzService.increaseLevel().subscribe();

    setTimeout(() => {
      this.showCards = true;
      this.cdr.detectChanges();
    }, 500);
  }

  quitter() {
  }
}

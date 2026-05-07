<<<<<<< Updated upstream
import { Component } from '@angular/core';
=======
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CardsService } from '../../service/cards-service';
import { Observable } from 'rxjs';
import { Ist } from '../../interface/ist';
import { CommonModule } from '@angular/common';
import { CardPlaceholder } from '../card-placeholder/card-placeholder';
import { QuizzService } from '../../service/quizz-service';
>>>>>>> Stashed changes

@Component({
  selector: 'app-booster',
  imports: [],
  templateUrl: './booster.html',
  styleUrl: './booster.css',
})
export class Booster {
  protected isOpen: boolean = false;
<<<<<<< Updated upstream
=======
  protected showCards: boolean = false;
  protected boosterCards: Ist[] = [];
  private cards: CardsService = inject(CardsService);
  private quizzService: QuizzService = inject(QuizzService);

  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

boosterOuverture() {
  console.log("boosterOuverture appelée");
    this.isOpen = true;

    this.cards.getThreeRandom().subscribe(cards => {
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
>>>>>>> Stashed changes
}

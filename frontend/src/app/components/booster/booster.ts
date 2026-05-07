import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CardsService } from '../../service/cards-service';
import { Observable } from 'rxjs';
import { Ist } from '../../interface/ist';
import { CommonModule } from '@angular/common';
import { CardPlaceholder } from '../card-placeholder/card-placeholder';

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

  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

boosterOuverture() {
    this.isOpen = true;
    
    this.cards.getThreeRandom().subscribe(cards => {
      this.boosterCards = cards;
      this.cdr.detectChanges();
    });

    setTimeout(() => {
      this.showCards = true;
      this.cdr.detectChanges();
    }, 500);
}

}

import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CardsService } from '../../service/cards-service';
import { Observable } from 'rxjs';
import { Ist } from '../../interface/ist';
import { CommonModule } from '@angular/common';
import { CardPlaceholder } from '../card-placeholder/card-placeholder';
import { Router } from '@angular/router';

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

  private router: Router = inject(Router);
  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  boosterOuverture() {
      this.cards.getThreeRandom().subscribe(cards => {
        this.boosterCards = cards;
        this.cdr.detectChanges();
      });

      this.isOpen = true;

      setTimeout(() => {
        this.showCards = true;
        this.cdr.detectChanges();
      }, 1300);
  }

  quitter() {
    this.router.navigate(['/collection']);
  }
}

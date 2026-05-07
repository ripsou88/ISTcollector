import { Component, inject } from '@angular/core';
import { Booster } from '../../components/booster/booster';
import { CommonModule } from '@angular/common';
import { CardsService } from '../../service/cards-service';

@Component({
  selector: 'app-testenora',
  imports: [CommonModule, Booster],
  templateUrl: './testenora.html',
  styleUrl: './testenora.css',
})
export class Testenora {
  protected showBooster: boolean = false;
  private cards: CardsService = inject(CardsService);

  boosterOuverture() {
    this.cards.getThreeRandom();
  }
}

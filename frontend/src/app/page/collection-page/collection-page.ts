import { Component, inject, OnInit } from '@angular/core';
import { CardPlaceholder } from '../../components/card-placeholder/card-placeholder';
import { Ist } from '../../interface/ist';
import { CardsService } from '../../service/cards-service';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { OwnedCardsResponse } from '../../interface/ownedCardsResponse';
import { CardDisplay } from '../../components/card-display/card-display';

@Component({
  selector: 'app-collection-page',
  imports: [CardPlaceholder, AsyncPipe, CardDisplay],
  templateUrl: './collection-page.html',
  styleUrl: './collection-page.css',
})
export class CollectionPage implements OnInit {
  private cardsService = inject(CardsService);
  protected ists$!: Observable<Ist[]>;
  protected userIsts$!: Observable<OwnedCardsResponse>;
  protected ownedIds = new Set<number>();

  ngOnInit(): void {
    this.ists$ = this.cardsService.findAll();
    this.userIsts$ = this.cardsService.getUserCards();
    this.userIsts$.subscribe((response) => {
      this.ownedIds = new Set(response.ownedIstIds);
    });
  }
}

import { AsyncPipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { CardDisplay } from '../../components/card-display/card-display';
import { Ist } from '../../interface/ist';
import { OwnedCardsResponse } from '../../interface/ownedCardsResponse';
import { CardsService } from '../../service/cards-service';

@Component({
  selector: 'app-collection-page',
  imports: [AsyncPipe, CardDisplay],
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

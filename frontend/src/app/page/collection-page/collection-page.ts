import { AsyncPipe } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { CardDisplay } from '../../components/card-display/card-display';
import { Ist } from '../../interface/ist';
import { OwnedCardsResponse } from '../../interface/ownedCardsResponse';
import { CardsService } from '../../service/cards-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-collection-page',
  imports: [AsyncPipe, CardDisplay, FormsModule],
  templateUrl: './collection-page.html',
  styleUrl: './collection-page.css',
})
export class CollectionPage implements OnInit {
  private cardsService = inject(CardsService);
  protected ists$!: Observable<Ist[]>;
  protected userIsts$!: Observable<OwnedCardsResponse>;
  protected ownedIds = new Set<number>();
  protected searchTerm = '';

  ngOnInit(): void {
    this.ists$ = this.cardsService.findAll();
    this.userIsts$ = this.cardsService.getUserCards();
    this.userIsts$.subscribe((response) => {
      this.ownedIds = new Set(response.ownedIstIds);
    });
  }

  protected filteredIsts(ists: Ist[]): Ist[] {
    const term = this.searchTerm.trim().toLowerCase();

    if (!term) {
      return ists;
    }

    return ists.filter((ist) => ist.nom.toLowerCase().includes(term));
  }
}

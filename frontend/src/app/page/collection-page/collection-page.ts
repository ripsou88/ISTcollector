import { AsyncPipe } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { CardDisplay } from '../../components/card-display/card-display';
import { Ist } from '../../interface/ist';
import { OwnedCardsResponse } from '../../interface/ownedCardsResponse';
import { CardsService } from '../../service/cards-service';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-collection-page',
  imports: [AsyncPipe, CardDisplay, FormsModule],
  templateUrl: './collection-page.html',
  styleUrl: './collection-page.css',
})
export class CollectionPage implements OnInit {
  private cardsService = inject(CardsService);
  private route = inject(ActivatedRoute);

  protected ists$!: Observable<Ist[]>;
  protected userIsts$!: Observable<OwnedCardsResponse>;
  protected ownedIds = new Set<number>();
  protected searchTerm = '';
  protected selectedIstName = signal<string | null>(null);

  ngOnInit(): void {
    this.ists$ = this.cardsService.findAll();
    this.userIsts$ = this.cardsService.getUserCards();
    this.userIsts$.subscribe((response) => {
      this.ownedIds = new Set(response.ownedIstIds);
    });

    this.route.queryParams.subscribe(params => {
      if (params['ist'] && params['modal'] === 'true') {
        const istParam = params['ist'];

        this.ists$.subscribe(ists => {
          const match = ists.find(ist =>
            istParam.toLowerCase().includes(ist.nom.toLowerCase())
          );
          if (match) {
            this.selectedIstName.set(match.nom);
          }
        });
      }
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

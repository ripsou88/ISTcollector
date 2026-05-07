import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable, combineLatest } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

import { CardPlaceholder } from '../../components/card-placeholder/card-placeholder';
import { Ist } from '../../interface/ist';
import { SearchService } from '../../service/search-service';

@Component({
  selector: 'app-collection-page',
  standalone: true,
  imports: [CommonModule, CardPlaceholder],
  templateUrl: './collection-page.html',
  styleUrl: './collection-page.css',
})
export class CollectionPage implements OnInit {

  private route = inject(ActivatedRoute);
  private searchService = inject(SearchService);

  protected ists$!: Observable<Ist[]>;

  private allIst: Ist[] = [
    { nom: 'VIH' } as Ist,
    { nom: 'Syphilis' } as Ist,
    { nom: 'Chlamydia' } as Ist,
    { nom: 'Gonorrhée' } as Ist,
    { nom: 'Hépatite B' } as Ist,
    { nom: 'Hépatite C' } as Ist,
    { nom: 'HPV' } as Ist,
    { nom: 'Herpès' } as Ist,
    { nom: 'Trichomonas' } as Ist,
    { nom: 'Mycoplasma' } as Ist,
  ];

  ngOnInit(): void {

    const routeSearch$ = this.route.paramMap.pipe(
      map(params => params.get('id')?.toLowerCase() ?? ''),
      startWith('')
    );

    const search$ = this.searchService.search$.pipe(
      startWith('')
    );

    this.ists$ = combineLatest([routeSearch$, search$]).pipe(
      map(([routeSearch, search]) => {

        const query = (search ?? routeSearch).toLowerCase().trim();

        if (!query) return this.allIst;

        return this.allIst.filter(i =>
          i.nom.toLowerCase().includes(query)
        );
      })
    );
  }
}
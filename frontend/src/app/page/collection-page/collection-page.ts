import { Component, inject, OnInit } from '@angular/core';
import { CardPlaceholder } from '../../components/card-placeholder/card-placeholder';
import { Ist } from '../../interface/ist';
import { TypeIst } from '../../enum/type-ist';
import { TypePrevention } from '../../enum/type-prevention';
import { Transmission } from '../../enum/transmission';
import { CardsService } from '../../service/cards-service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-collection-page',
  imports: [CardPlaceholder],
  templateUrl: './collection-page.html',
  styleUrl: './collection-page.css',
})
export class CollectionPage implements OnInit {
  private cardsService = inject(CardsService);
  protected ists$!: Observable<Ist[]>;
  protected vih: Ist = {
    id: 1,
    nom: 'vih',
    gravite: 5,
    incidence: 5000,
    symptomes: ['Generique'],
    shortDesc: 'sympathic disease',
    desc: 'Le VIH est un retrovirus qui va s’attaquer au systeme immunitaire et plus specifiquement aux lymphocyte T CD4, qui au stade final d’infection est connu sous le nom de sida',
    typeIst: TypeIst.Virale,
    traitements: [{ id: 1, nom: 'Antiretroviral', prise: 'Idk', duree: -1 }],
    preventions: [{ id: 1, nom: 'Preservatif', typePrevention: TypePrevention.Barriere }],
    transmissions: [
      Transmission.Contact_Sanguin,
      Transmission.Materno_Foetale,
      Transmission.Sexuelle,
    ],
  };

  protected cards: Ist[] = Array.from({ length: 10 }, (_, i) => ({
    ...this.vih,
    id: i + 1,
  }));

  ngOnInit(): void {
    this.ists$ = this.cardsService.findAll();
    console.log(this.ists$);
  }
}

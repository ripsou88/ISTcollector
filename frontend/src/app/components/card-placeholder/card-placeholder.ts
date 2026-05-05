import { Component, input } from '@angular/core';
import { Ist } from '../../interface/ist';
import { CardPopup } from '../card-popup/card-popup';
import { Transmission } from '../../enum/transmission';
import { TypeIst } from '../../enum/type-ist';
import { TypePrevention } from '../../enum/type-prevention';

@Component({
  selector: 'app-card-placeholder',
  imports: [CardPopup],
  templateUrl: './card-placeholder.html',
  styleUrl: './card-placeholder.css',
})
export class CardPlaceholder {
  ist = input.required<Ist>();

  protected vih: Ist = {
    id: 1,
    nom: 'vih',
    gravite: 5,
    img: '',
    incidence: 5000,
    symptome: [],
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
}

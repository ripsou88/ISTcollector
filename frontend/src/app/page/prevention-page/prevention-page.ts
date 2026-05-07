import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-prevention-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './prevention-page.html',
  styleUrls: ['./prevention-page.css']
})

export class PreventionPage {

  constructor(private router: Router) {}

startPrevention() {
  this.router.navigate(['/prevention']);
}

  preventionList = [
    { name: 'Préservatif', type: 'barriere' },
    { name: 'Digue dentaire', type: 'barriere' },
    { name: 'Dépistage', type: 'medical' },
    { name: 'Vaccination', type: 'medical' },
    { name: 'PrEP VIH', type: 'medical' },
    { name: 'TPE VIH', type: 'medical' },
    { name: 'Matériel stérile', type: 'comportement' },
    { name: 'Éviter les poussées', type: 'comportement' },
    { name: 'Partenaire traité', type: 'comportement' },
    { name: 'Ne pas partager d’objets', type: 'comportement' },
    { name: 'Hygiène du linge', type: 'comportement' },
    { name: 'Examen prénatal', type: 'medical' },
    { name: 'Limiter les partenaires', type: 'comportement' },
    { name: 'Informer le partenaire', type: 'comportement' }
  ];
}
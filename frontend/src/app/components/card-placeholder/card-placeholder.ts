import { Component, AfterViewInit, ViewChild, ElementRef, input } from '@angular/core';
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
export class CardPlaceholder implements AfterViewInit{
  ist = input.required<Ist>();

  protected vih: Ist = {
    id: 1,
    nom: 'vih',
    gravite: 5,
    img: '',
    incidence: 5000,
    symptome: [],
    shortDesc: 'sympathic disease',
    desc: `Le VIH est un retrovirus qui va s’attaquer au systeme immunitaire et plus specifiquement aux lymphocyte T CD4, qui au stade final d’infection est connu sous le nom de sida`,
    typeIst: TypeIst.Virale,
    traitements: [{ id: 1, nom: 'Antiretroviral', prise: 'Idk', duree: -1 }],
    preventions: [{ id: 1, nom: 'Preservatif', typePrevention: TypePrevention.Barriere }],
    transmissions: [
      Transmission.Contact_Sanguin,
      Transmission.Materno_Foetale,
      Transmission.Sexuelle,
    ],
  };

  @ViewChild('allcarte') wrapRef!: ElementRef;
  @ViewChild('carte') cardRef!: ElementRef;
  @ViewChild('foil') foilRef!: ElementRef;
  @ViewChild('shimmer') shimmerRef!: ElementRef;
  @ViewChild('glare') glareRef!: ElementRef;

    ngAfterViewInit() {
    const wrap = this.wrapRef.nativeElement;
    const card = this.cardRef.nativeElement;
    const foil = this.foilRef.nativeElement;
    const shimmer = this.shimmerRef.nativeElement;
    const glare = this.glareRef.nativeElement;

    let bounds: DOMRect | null = null;
    let raf: number;

    function refreshBounds() {
      bounds = wrap!.getBoundingClientRect();
    }

    function applyTilt(e: MouseEvent | { clientX: number, clientY: number }) {
      if (!bounds) refreshBounds();

      const mx = e.clientX - bounds!.left;
      const my = e.clientY - bounds!.top;
      const mxPct = mx / bounds!.width;
      const myPct = my / bounds!.height;

      const rotateY = -(mxPct - 0.5) * 46;
      const rotateX = (myPct - 0.5) * 38;
      const shadowX = -rotateY * 0.6;
      const shadowY = rotateX * 0.6;
      const angle = Math.atan2(myPct - 0.5, mxPct - 0.5) * (180 / Math.PI);

      card!.style.transform = `rotateX(${rotateX}deg) rotateY(${rotateY}deg) scale(1.04)`;
      foil!.style.setProperty('--mx', `${mxPct * 100}%`);
      foil!.style.setProperty('--my', `${myPct * 100}%`);
      glare!.style.setProperty('--mx', `${mxPct * 100}%`);
      glare!.style.setProperty('--my', `${myPct * 100}%`);
      shimmer!.style.setProperty('--angle', `${angle + 90}deg`);
    }

    function resetTilt() {
      card!.style.transform = 'rotateX(0deg) rotateY(0deg) scale(1)';
    }

    wrap!.addEventListener('mouseenter', refreshBounds);
    wrap!.addEventListener('mousemove', (e: MouseEvent) => {
      cancelAnimationFrame(raf);
      raf = requestAnimationFrame(() => applyTilt(e));
    });
    wrap!.addEventListener('mouseleave', resetTilt);
  }
}

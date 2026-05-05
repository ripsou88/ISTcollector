import { Ist } from '../../interface/ist';
import { CardPopup } from '../card-popup/card-popup';
import { Component, AfterViewInit, ViewChild, ElementRef, input, signal } from '@angular/core';
import { TransmissionEmoji } from '../../enum/transmission';
import { TypeIstEmoji } from '../../enum/type-ist';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-card-placeholder',
  imports: [CardPopup, NgFor],
  templateUrl: './card-placeholder.html',
  styleUrl: './card-placeholder.css',
})
export class CardPlaceholder implements AfterViewInit {
  public readonly ist = input.required<Ist>();

  protected readonly isModalOpen = signal(false);
  protected readonly isAnimating = signal(false);

  protected openModal(): void {
    if (this.isAnimating() || this.isModalOpen()) {
      return;
    }

    this.isAnimating.set(true);

    setTimeout(() => {
      this.isModalOpen.set(true);
      this.isAnimating.set(false);
    }, 300);
  }

  protected closeModal(): void {
    this.isModalOpen.set(false);
  }
  protected typeIstEmoji = TypeIstEmoji;
  protected transmissionEmoji = TransmissionEmoji;

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

    function applyTilt(e: MouseEvent | { clientX: number; clientY: number }) {
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

  getStars(): number[] {
    const stars = [];
    for (let i = 0; i < this.ist().gravite; i++) {
      stars.push(i);
    }
    return stars;
  }
}

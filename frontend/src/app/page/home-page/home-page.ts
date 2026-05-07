import { Component, AfterViewInit } from '@angular/core';
import { Hero } from '../../components/hero/hero';
import { Cards } from '../../components/cards/cards';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [Hero, Cards, FormsModule],
  templateUrl: './home-page.html',
  styleUrls: ['./home-page.css'],
})

export class HomePage implements AfterViewInit {

  //Menu hamburger
  menuActive = false;

  constructor(private router: Router) {}

  toggleMenu() {
    this.menuActive = !this.menuActive;
  }


  goQuizz() {
    this.router.navigate(['/quizz']);
  }

  // Recherche IST

  searchTerm = '';

  allIST = [
  'VIH',
  'Chlamydiose',
  'Gonorrhée',
  'Syphilis',
  'Herpès génital',
  'Papillomavirus (HPV)',
  'Hépatite B',
  'Hépatite C',
  'Trichomonase',
  'Mycoplasma genitalium',
  'Lymphogranulomatose vénérienne',
  'Chancre mou',
  'Donovanose',
  'Gale',
  'Poux pubiens',
  'Molluscum contagiosum'
];

  filteredIST = this.allIST;

  searchIST() {
    this.filteredIST = this.allIST.filter(ist =>
      ist.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  //Bulle
  bubbleVisible = false;

  ngAfterViewInit() {
    const trigger = document.getElementById('middle-cards');
    const bubble = document.querySelector('.bubble') as HTMLElement;

    if (!trigger || !bubble) return;

    const observer = new IntersectionObserver(entries => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          bubble.classList.add('show');
          observer.disconnect();
        }
      });
    }, { threshold: 0.3 });

    observer.observe(trigger);
  }
}

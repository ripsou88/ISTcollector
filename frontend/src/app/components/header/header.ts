import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SearchService } from '../../service/search-service';
import { AuthService } from '../../service/auth-service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule, CommonModule, FormsModule],
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
})
export class Header implements OnInit {

  private router = inject(Router);
  private searchService = inject(SearchService);
  private authService = inject(AuthService);

  menuActive = false;
  searchTerm = '';

allIST: string[] = [
  'VIH / Sida',
  'Syphilis',
  'Chlamydia',
  'Gonorrhée',
  'Hépatite A',
  'Hépatite B',
  'Hépatite C',
  'Herpès génital (HSV-1 / HSV-2)',
  'HPV (Papillomavirus)',
  'Trichomonase',
  'Mycoplasma genitalium',
  'Uréaplasma',
  'Chancre mou (Haemophilus ducreyi)',
  'Lymphogranulomatose vénérienne (LGV)',
  'Molluscum contagiosum',
  'Gale',
  'Poux du pubis (Phtirius pubis)',
  'Candidose génitale',
  'Vaginose bactérienne'
];

  filteredIST: string[] = [];

  ngOnInit(): void {
    this.filteredIST = [...this.allIST];
  }

  toggleMenu() {
    this.menuActive = !this.menuActive;
  }

  searchIST() {
    const value = this.searchTerm.trim().toLowerCase();

    if (!value) {
      this.filteredIST = [...this.allIST];
      return;
    }

    this.filteredIST = this.allIST.filter(ist =>
      ist.toLowerCase().includes(value)
    );

    this.searchService.setSearch(value);
  }

  goToIST(ist: string) {
    const value = ist.trim().toLowerCase();

    this.searchService.setSearch(value);

    this.router.navigate(['/collection', value]); // 👈 mieux avec param
    this.menuActive = false;
    this.searchTerm = '';
    this.filteredIST = [...this.allIST];
  }

  closeMenu() {
    this.menuActive = false;
  }

  isLogged(): boolean {
    return this.authService.isLogged();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/home']);
    this.closeMenu();
  }
}
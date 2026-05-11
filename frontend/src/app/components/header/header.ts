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

  allIST = [
    { label: 'VIH / Sida', nom: 'vih' },
    { label: 'Syphilis', nom: 'syphilis' },
    { label: 'Chlamydia', nom: 'chlamydia' },
    { label: 'Gonorrhée', nom: 'gonorrhee' },
    { label: 'Hépatite B', nom: 'hepatite_b' },
    { label: 'Hépatite C', nom: 'hepatite_c' },
    { label: 'Herpès génital (HSV-1 / HSV-2)', nom: 'herpes_genital' },
    { label: 'HPV (Papillomavirus)', nom: 'papillomavirus' },
    { label: 'Trichomonase', nom: 'trichomonase' },
    { label: 'Mycoplasma genitalium', nom: 'mycoplasma' },
    { label: 'Chancre mou (Haemophilus ducreyi)', nom: 'chancre_mou' },
    { label: 'Lymphogranulomatose vénérienne (LGV)', nom: 'lgv' },
    { label: 'Molluscum contagiosum', nom: 'molluscum' },
    { label: 'Gale', nom: 'gale' },
    { label: 'Poux du pubis (Phtirius pubis)', nom: 'poux_pubiens' },
    { label: 'Donovanose', nom: 'donovanose' },
  ];

  filteredIST: { label: string, nom: string }[] = [];

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
      ist.label.toLowerCase().includes(value)
    );

    this.searchService.setSearch(value);
  }

  goToIST(ist: { label: string, nom: string }) {
    this.router.navigate(['/collection'], {
      queryParams: { ist: ist.nom, modal: true }
    });
    
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
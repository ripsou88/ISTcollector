import { Routes } from '@angular/router';
import { AuthPage } from './page/auth-page/auth-page';
import { HomePage } from './page/home-page/home-page';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePage, title: 'ISTédex - Accueil' },
  { path: 'connexion', component: AuthPage, title: 'Connexion' },
  { path: 'guides', component: HomePage, title: 'Guides IST' },
  { path: 'collector', component: HomePage, title: 'Collection IST' }
];

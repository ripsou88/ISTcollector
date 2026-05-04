import { Routes } from '@angular/router';
import { AuthPage } from './page/auth-page/auth-page';
import { HomePage } from './page/home-page/home-page';
import { CollectionPage } from './page/collection-page/collection-page';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePage, title: 'ISTédex - Accueil' },
  { path: 'connexion', component: AuthPage, title: 'Connexion' },
  { path: 'guides', component: HomePage, title: 'Guides IST' },
  { path: 'collection', component: CollectionPage, title: 'Collection IST' },
];

import { Routes } from '@angular/router';
import { AuthPage } from './page/auth-page/auth-page';
import { HomePage } from './page/home-page/home-page';

export const routes: Routes = [
    { path: 'home', component: HomePage, title: "Home Page" },
    { path: 'login', component: AuthPage, title: "Liste des Auteurs" },

    { path: '', redirectTo: 'home', pathMatch: 'full' }
];

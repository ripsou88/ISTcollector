import { Routes } from '@angular/router';
import { JeuxComponent } from './components/jeux/jeux';
import { adminGuard } from './guard/admin-guard';
import { authGuard } from './guard/auth-guard';
import { AuthPage } from './page/auth-page/auth-page';
import { CollectionPage } from './page/collection-page/collection-page';
import { HomePage } from './page/home-page/home-page';
import { PreventionPage } from './page/prevention-page/prevention-page';
import { QuestionReponsePage } from './page/question-reponse-page/question-reponse-page';
import { Quizz } from './page/quizz/quizz';
import { Testenora } from './page/testenora/testenora';
import { Error404 } from './page/error404/error404';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePage, title: 'ISTédex - Accueil' },
  { path: 'connexion', component: AuthPage, title: 'Connexion' },
  { path: 'guides', component: HomePage, title: 'Guides IST' },
  { path: 'collection', component: CollectionPage, title: 'Collection IST' , canActivate: [authGuard]},
    { path: 'collection/:id', component: CollectionPage },
  { path: 'prevention', component: PreventionPage, title: 'Prévention IST' },
  { path: 'questionReponse', component: QuestionReponsePage, title: 'Questions / Reponses', canActivate: [adminGuard] },
  { path: 'jeux', component: JeuxComponent, title: 'Jeux' },
  { path: 'quizz', component: Quizz, title: 'Quizz', canActivate: [authGuard] },
  { path: 'testenora', component: Testenora, title: 'Tests Enora' },

  { path: '**', component: Error404 } // Route vers page Erreur 404 -> Doit être le dernier choix
];

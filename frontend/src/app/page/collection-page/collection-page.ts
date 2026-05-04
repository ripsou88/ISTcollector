import { Component } from '@angular/core';
import { CardPlaceholder } from '../../components/card-placeholder/card-placeholder';

@Component({
  selector: 'app-collection-page',
  imports: [CardPlaceholder],
  templateUrl: './collection-page.html',
  styleUrl: './collection-page.css',
})
export class CollectionPage {
  protected cards = [1, 2, 3, 4, 5, 6, 7, 8];
}

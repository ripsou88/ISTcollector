<<<<<<< Updated upstream
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CardsService } from '../../service/cards-service';
import { Observable } from 'rxjs';
import { Ist } from '../../interface/ist';
import { CommonModule } from '@angular/common';
import { CardPlaceholder } from '../card-placeholder/card-placeholder';
=======
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { CardsService } from '../../service/cards-service';
import { Observable } from 'rxjs';
import { Ist } from '../../interface/ist';
import { CommonModule } from '@angular/common';
import { CardPlaceholder } from '../card-placeholder/card-placeholder';
import { QuizzService } from '../../service/quizz-service';
>>>>>>> Stashed changes

@Component({
  selector: 'app-booster',
  imports: [CommonModule, CardPlaceholder],
  templateUrl: './booster.html',
  styleUrl: './booster.css',
})
export class Booster {
  protected isOpen: boolean = false;
}

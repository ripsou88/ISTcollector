import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cards',
  templateUrl: './cards.html',
  styleUrls: ['./cards.css'],
})
export class Cards {

constructor(private router: Router) {}

  goQuizz() {
    this.router.navigate(['/quizz']);
  }

}
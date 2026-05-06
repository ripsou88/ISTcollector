import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-cards',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './cards.html',
  styleUrls: ['./cards.css'],
})
export class Cards {

constructor(private router: Router) {}

  goQuizz() {
    this.router.navigate(['/quizz']);
  }

}
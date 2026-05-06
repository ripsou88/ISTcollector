import { Component } from '@angular/core';

@Component({
  selector: 'app-quizz',
  standalone: true,
  templateUrl: './quizz.html',
  styleUrls: ['./quizz.css']
})
export class QuizzComponent {

  gameStarted = false;

  startQuizz() {
    this.gameStarted = true;
  }
}
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-quizz',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './quizz.html',
  styleUrls: ['./quizz.css']
})
export class QuizzComponent {

  gameStarted = false;

  startQuizz() {
    this.gameStarted = true;
  }
}
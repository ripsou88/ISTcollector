import { Booster } from './../../components/booster/booster';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { finalize, timeout } from 'rxjs';
import { Question } from '../../interface/question';
import { QuizzService } from '../../service/quizz-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-quizz',
  imports: [CommonModule,Booster],
  templateUrl: './quizz.html',
  styleUrl: './quizz.css',
})
export class Quizz {
  protected gameStarted = false;
  protected questions: Question[] = [];
  protected selectedReponses: Record<number, number> = {};
  protected resultMessage = '';
  protected canRetry = false;
  protected loading = false;
  protected validating = false;
  protected showBooster: boolean = false;


  private quizzService: QuizzService = inject(QuizzService);
  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  protected startQuizz(): void {
    this.gameStarted = true;
    this.loadQuestions();
  }

  protected selectReponse(questionId: number, reponseId: number): void {
    this.selectedReponses[questionId] = reponseId;
    this.resultMessage = '';
    this.canRetry = false;
  }

  protected isSelected(questionId: number, reponseId: number): boolean {
    return this.selectedReponses[questionId] === reponseId;
  }

  protected checkQuizz(): void {
    if (this.validating) {
      return;
    }

    const hasEverythingCorrect = this.questions.length > 0 && this.questions.every((question) => {
      const correctReponse = question.reponses.find((reponse) => reponse.correct);
      return this.selectedReponses[question.id] === correctReponse?.id;
    });

    if (hasEverythingCorrect) {
      this.resultMessage = 'Super ! Toutes tes reponses sont justes. Voici ta recompense !';
      this.canRetry = false;
      this.validating = true;
      // TODO penser a appeler increase level dans le component boooster
      //this.increaseLevel();
      return;
    }

    this.resultMessage = "Tu n'as pas tout juste. Tu peux refaire le quizz.";
    this.canRetry = true;
  }

  protected retryQuizz(): void {
    this.loadQuestions();
  }

  private loadQuestions(): void {
    this.loading = true;
    this.questions = [];
    this.selectedReponses = {};
    this.resultMessage = '';
    this.canRetry = false;

    this.quizzService.getTenQuestions()
      .pipe(
        timeout(10000),
        finalize(() => {
          this.loading = false;
          this.cdr.detectChanges();
        }),
      )
      .subscribe({
        next: (questions) => {
          this.questions = questions;
        },
        error: () => {
          this.resultMessage = 'Impossible de charger le quizz pour le moment.';
        },
      });
  }

  protected openBooster(): void {
    this.showBooster = true;
  }


}

import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { finalize, timeout } from 'rxjs';
import { Question } from '../../interface/question';
import { QuizzService } from '../../service/quizz-service';

@Component({
  selector: 'app-quizz',
  imports: [],
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
      this.resultMessage = '';
      this.canRetry = false;
      this.increaseLevel();
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

  private increaseLevel(): void {
    this.validating = true;

    this.quizzService.increaseLevel()
      .pipe(finalize(() => {
        this.validating = false;
        this.cdr.detectChanges();
      }))
      .subscribe({
        next: () => {
          // TODO: implementer l'ouverture de booster.
        },
        error: () => {
          this.resultMessage = "Le quizz est reussi, mais le niveau n'a pas pu etre mis a jour.";
        },
      });
  }
}

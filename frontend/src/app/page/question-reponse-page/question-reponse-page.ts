import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Question } from '../../interface/question';
import { QuestionReponseService } from '../../service/question-reponse-service';

interface ReponseForm {
  reponse: string;
  correct: boolean;
}

@Component({
  selector: 'app-question-reponse-page',
  imports: [CommonModule, FormsModule],
  templateUrl: './question-reponse-page.html',
  styleUrl: './question-reponse-page.css',
})
export class QuestionReponsePage implements OnInit {
  private questionReponseService: QuestionReponseService = inject(QuestionReponseService);
  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  protected idQuestionEnEdition?: number;
  protected question: string = '';
  protected reponses: ReponseForm[] = [
    { reponse: '', correct: true },
    { reponse: '', correct: false },
  ];
  protected questions: Question[] = [];
  protected loadingQuestions: boolean = false;
  protected successMessage: string = '';
  protected errorMessage: string = '';

  ngOnInit(): void {
    this.loadQuestions();
  }

  protected addReponse(): void {
    this.reponses.push({ reponse: '', correct: false });
  }

  protected removeReponse(index: number): void {
    if (this.reponses.length > 1) {
      this.reponses.splice(index, 1);
    }
  }

  protected saveQuestion(): void {
    const request = {
      question: this.question,
      reponses: this.reponses,
    };

    if (this.idQuestionEnEdition) {
      this.questionReponseService.updateQuestion(this.idQuestionEnEdition, request).subscribe({
        next: () => this.afterSave('Question modifiee.'),
        error: () => this.errorMessage = 'Erreur lors de la modification.',
      });
      return;
    }

    this.questionReponseService.createQuestion(request).subscribe({
      next: () => this.afterSave('Question ajoutee.'),
      error: () => this.errorMessage = 'Erreur lors de la creation.',
    });
  }

  protected editQuestion(question: Question): void {
    this.idQuestionEnEdition = question.id;
    this.question = question.question;
    this.reponses = question.reponses.map((reponse) => ({
      reponse: reponse.reponse,
      correct: reponse.correct,
    }));
    this.successMessage = '';
    this.errorMessage = '';
  }

  protected deleteQuestion(id: number): void {
    this.questionReponseService.deleteQuestion(id).subscribe({
      next: () => {
        this.afterSave('Question supprimee.');
      },
      error: () => this.errorMessage = 'Erreur lors de la suppression.',
    });
  }

  protected cancelEdit(): void {
    this.resetForm();
    this.successMessage = '';
    this.errorMessage = '';
  }

  private loadQuestions(): void {
    this.loadingQuestions = true;

    this.questionReponseService.findAllQuestions().subscribe({
      next: (questions) => {
        this.questions = questions;
        this.loadingQuestions = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.loadingQuestions = false;
        this.errorMessage = 'Erreur lors du chargement des questions.';
        this.cdr.detectChanges();
      },
    });
  }

  private afterSave(message: string): void {
    this.resetForm();
    this.loadQuestions();
    this.errorMessage = '';
    this.successMessage = message;
  }

  private resetForm(): void {
    this.idQuestionEnEdition = undefined;
    this.question = '';
    this.reponses = [
      { reponse: '', correct: true },
      { reponse: '', correct: false },
    ];
  }
}

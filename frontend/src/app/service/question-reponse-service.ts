import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreateOrUpdateQuestionRequest } from '../dto/create-or-update-question-request';
import { Question } from '../interface/question';

@Injectable({
  providedIn: 'root',
})
export class QuestionReponseService {
  private http: HttpClient = inject(HttpClient);

  public findAllQuestions(): Observable<Question[]> {
    return this.http.get<Question[]>('/question');
  }

  public createQuestion(request: CreateOrUpdateQuestionRequest): Observable<{ id: number }> {
    return this.http.post<{ id: number }>('/question', request);
  }

  public updateQuestion(id: number, request: CreateOrUpdateQuestionRequest): Observable<{ id: number }> {
    return this.http.put<{ id: number }>(`/question/${id}`, request);
  }

  public deleteQuestion(id: number): Observable<void> {
    return this.http.delete<void>(`/question/${id}`);
  }
}

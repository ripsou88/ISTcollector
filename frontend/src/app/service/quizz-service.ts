import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Question } from '../interface/question';

@Injectable({
  providedIn: 'root',
})
export class QuizzService {
  private http: HttpClient = inject(HttpClient);

  public getTenQuestions(): Observable<Question[]> {
    return this.http.get<Question[]>('/question/ten');
  }

  public increaseLevel(): Observable<void> {
    return this.http.get<void>('/quizz/increase_level');
  }

}

import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ist } from '../interface/ist';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class CardsService {
  private http = inject(HttpClient);

  public findAll(): Observable<Ist[]> {
    return this.http.get<Ist[]>('/ist');
  }

  public getThreeRandom(): Observable<Ist[]> {
    return this.http.get<Ist[]>("/quizz/random_card");
  }
}

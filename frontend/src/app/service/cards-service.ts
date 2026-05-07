import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Ist } from '../interface/ist';
import { HttpClient } from '@angular/common/http';
import { AuthRequest } from '../dto/auth-request';
import { OwnedCardsResponse } from '../interface/ownedCardsResponse';

@Injectable({
  providedIn: 'root',
})
export class CardsService {
  private http = inject(HttpClient);

  public findAll(): Observable<Ist[]> {
    return this.http.get<Ist[]>('/ist');
  }

  public getThreeRandom(): Observable<Ist[]> {
    return this.http.get<Ist[]>('/random_card');
  }

  public getUserCards(): Observable<OwnedCardsResponse> {
    return this.http.get<OwnedCardsResponse>('/cards');
  }
}

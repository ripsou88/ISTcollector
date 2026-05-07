import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthRequest } from '../dto/auth-request';
import { AuthResponse } from '../dto/auth-response';
import { SubResponse } from '../dto/sub-response';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _token: string = sessionStorage.getItem('token') ?? '';

  public get token(): string {
    return this._token;
  }

  public set token(value: string) {
    this._token = value;
    sessionStorage.setItem('token', value);
  }

  constructor(private http: HttpClient) {}

  public auth(authRequest: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>('/compte/auth', authRequest);
  }

  public sub(authRequest: AuthRequest): Observable<SubResponse> {
    return this.http.post<SubResponse>('/compte/subscription', authRequest);
  }

  public isLogged(): boolean {
    return !!this._token;
  }

  public isAdmin(): boolean {
    return this.getTokenRole() === 'ROLE_ADMIN';
  }

  private getTokenRole(): string {
    if (!this._token) {
      return '';
    }

    try {
      const payload = this._token.split('.')[1];
      const base64Payload = payload
        .replace(/-/g, '+')
        .replace(/_/g, '/')
        .padEnd(Math.ceil(payload.length / 4) * 4, '=');
      const decodedPayload = JSON.parse(atob(base64Payload));

      return decodedPayload.role ?? '';
    } catch {
      return '';
    }
  }
}

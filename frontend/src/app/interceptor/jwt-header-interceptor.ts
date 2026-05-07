import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../service/auth-service';
import { inject } from '@angular/core';

export const jwtHeaderInterceptor: HttpInterceptorFn = (req, next) => {
  const authService: AuthService = inject(AuthService);

  // Si on appelle l'authentification, on n'injecte pas le Authorization
  if (req.url.endsWith('/api/compte/auth') || req.url.endsWith('/api/compte/subscription') || !authService.token) {
    return next(req);
  }

  const jwtRequest = req.clone({
    setHeaders: {
      'Authorization': `Bearer ${ authService.token }`
    }
  });

  return next(jwtRequest);
};

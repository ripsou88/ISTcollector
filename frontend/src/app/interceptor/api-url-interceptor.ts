import { HttpInterceptorFn } from '@angular/common/http';
import {BACK_URL} from './../../../.env';

export const apiUrlInterceptor: HttpInterceptorFn = (req, next) => {

  const apiRequest = req.clone({
    url: BACK_URL+"/api" + req.url
  });

  return next(apiRequest);
};

import { provideRouter, withInMemoryScrolling } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { apiUrlInterceptor } from './interceptor/api-url-interceptor';
import { jwtHeaderInterceptor } from './interceptor/jwt-header-interceptor';

export const appConfig = {
  providers: [
<<<<<<< HEAD
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(
// On configure le client HTTP et un ou plusieurs intercepteurs de requête HTTP
withInterceptors([ apiUrlInterceptor, jwtHeaderInterceptor ])
)
=======
    provideRouter(
      routes,
      withInMemoryScrolling({
        scrollPositionRestoration: 'top'
      })
    )
>>>>>>> Camille
  ]
};
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-error404',
  imports: [RouterModule],
  templateUrl: './error404.html',
  styleUrl: './error404.css',
})
export class Error404 {
  constructor(private router: Router) {}
}

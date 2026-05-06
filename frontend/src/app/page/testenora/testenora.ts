import { Component } from '@angular/core';
import { Booster } from '../../components/booster/booster';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-testenora',
  imports: [CommonModule, Booster],
  templateUrl: './testenora.html',
  styleUrl: './testenora.css',
})
export class Testenora {
  protected showBooster: boolean = false;
}

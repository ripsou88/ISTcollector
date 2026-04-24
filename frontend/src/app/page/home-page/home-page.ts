import { Component, AfterViewInit} from '@angular/core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.html',
  styleUrls: ['./home-page.css'],
})
export class HomePage implements AfterViewInit {

  //Menu hamburger
  menuActive = false;

  toggleMenu() {
    this.menuActive = !this.menuActive;
  }

  //Bulle
  bubbleVisible = false;

  ngAfterViewInit() {
    const trigger = document.querySelector('#middle-cards');
    const bubble = document.querySelector('.bubble');

    const observer = new IntersectionObserver(entries => {
      entries.forEach(entry => {
        if (entry.isIntersecting && bubble) {
          bubble.classList.add('show');
          observer.disconnect();
        }
      });
    }, { threshold: 0.3 });

    if (trigger) {
      observer.observe(trigger);
    }
  }
}

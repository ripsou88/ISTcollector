import { Component } from '@angular/core';

@Component({
  selector: 'app-home-page',
  imports: [],
  templateUrl: './home-page.html',
  styleUrl: './home-page.css',
})
export class HomePage {

}


/* Il faut convertir le js en ts je pense
document.addEventListener("DOMContentLoaded", function() {
  const bubble = document.querySelector(".bubble");
  const trigger = document.querySelector("#middle-cards");

  const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
      if(entry.isIntersecting) {
        bubble.classList.add("show");
        observer.unobserve(trigger);
      }
    });
  }, { threshold: 0.3 });

  observer.observe(trigger);
});
*/

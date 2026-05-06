import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Testenora } from './testenora';

describe('Testenora', () => {
  let component: Testenora;
  let fixture: ComponentFixture<Testenora>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Testenora]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Testenora);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

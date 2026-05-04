import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardPopup } from './card-popup';

describe('CardPopup', () => {
  let component: CardPopup;
  let fixture: ComponentFixture<CardPopup>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardPopup]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardPopup);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

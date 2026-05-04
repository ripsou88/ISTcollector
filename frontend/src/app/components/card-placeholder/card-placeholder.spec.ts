import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardPlaceholder } from './card-placeholder';

describe('CardPlaceholder', () => {
  let component: CardPlaceholder;
  let fixture: ComponentFixture<CardPlaceholder>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardPlaceholder]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardPlaceholder);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

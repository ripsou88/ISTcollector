import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Booster } from './booster';

describe('Booster', () => {
  let component: Booster;
  let fixture: ComponentFixture<Booster>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Booster]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Booster);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

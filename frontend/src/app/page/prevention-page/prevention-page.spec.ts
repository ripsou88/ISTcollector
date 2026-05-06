import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PreventionPage } from './prevention-page';

describe('PreventionPage', () => {
  let component: PreventionPage;
  let fixture: ComponentFixture<PreventionPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PreventionPage]
    }).compileComponents();

    fixture = TestBed.createComponent(PreventionPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
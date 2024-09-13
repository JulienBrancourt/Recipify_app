import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InsctructionCardComponent } from './insctruction-card.component';

describe('InsctructionCardComponent', () => {
  let component: InsctructionCardComponent;
  let fixture: ComponentFixture<InsctructionCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InsctructionCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InsctructionCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

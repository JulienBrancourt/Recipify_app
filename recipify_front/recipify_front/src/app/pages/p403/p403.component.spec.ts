import { ComponentFixture, TestBed } from '@angular/core/testing';

import { P403Component } from './p403.component';

describe('P403Component', () => {
  let component: P403Component;
  let fixture: ComponentFixture<P403Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [P403Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(P403Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

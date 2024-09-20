import { TestBed } from '@angular/core/testing';

import { BehaviourSubjectService } from './behaviour-subject.service';

describe('BehaviourSubjectService', () => {
  let service: BehaviourSubjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BehaviourSubjectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

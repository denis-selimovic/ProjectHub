import { TestBed } from '@angular/core/testing';

import { HttpClientModule } from '@angular/common/http';

import { GuardService } from '../guard/guard.service';

describe('GuardService', () => {
  let service: GuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientModule ]
    });
    service = TestBed.inject(GuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

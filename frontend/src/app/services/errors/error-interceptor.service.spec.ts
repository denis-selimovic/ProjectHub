import { TestBed } from '@angular/core/testing';

import { ErrorInterceptorService } from './error-interceptor.service';
import { HttpClientModule } from '@angular/common/http';

describe('ErrorInterceptorService', () => {
  let service: ErrorInterceptorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientModule ]
    });
    service = TestBed.inject(ErrorInterceptorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

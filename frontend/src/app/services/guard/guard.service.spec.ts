import { TestBed } from '@angular/core/testing';

import { HttpClientModule } from '@angular/common/http';

import { GuardService } from '../guard/guard.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AppRoutingModule } from 'src/app/app-routing.module';

describe('GuardService', () => {
  let service: GuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        AppRoutingModule,
        HttpClientModule,
      ],
      providers: [
        HttpClientModule
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    });
    service = TestBed.inject(GuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

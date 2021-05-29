import { TestBed } from '@angular/core/testing';

import { HttpClientModule } from '@angular/common/http';

import { CollaboratorService } from './collaborator.service';

describe('CollaboratorService', () => {
  let service: CollaboratorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientModule ]
    });
    service = TestBed.inject(CollaboratorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

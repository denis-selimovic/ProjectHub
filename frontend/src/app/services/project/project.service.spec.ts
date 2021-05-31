import { TestBed } from '@angular/core/testing';

import { CommonTestingModule } from 'src/app/common-testing.module';

import { ProjectService } from './project.service';

describe('ProjectService', () => {
  let service: ProjectService;

  CommonTestingModule.setUpTestBed(ProjectService);

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

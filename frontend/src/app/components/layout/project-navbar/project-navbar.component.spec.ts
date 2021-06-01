import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppRoutingModule } from 'src/app/app-routing.module';

import { ProjectNavbarComponent } from './project-navbar.component';

describe('ProjectNavbarComponent', () => {
  let component: ProjectNavbarComponent;
  let fixture: ComponentFixture<ProjectNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ 
        AppRoutingModule,
        HttpClientModule 
      ],
      declarations: [ ProjectNavbarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

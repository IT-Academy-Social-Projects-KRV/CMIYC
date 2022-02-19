import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminUserFormComponent } from './admin-user-form.component';

describe('AdminUserFormComponent', () => {
  let component: AdminUserFormComponent;
  let fixture: ComponentFixture<AdminUserFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminUserFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminUserFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

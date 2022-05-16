import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminUserUpdateFormComponent } from './admin-user-update-form.component';

describe('AdminUserUpdateFormComponent', () => {
  let component: AdminUserUpdateFormComponent;
  let fixture: ComponentFixture<AdminUserUpdateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminUserUpdateFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminUserUpdateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

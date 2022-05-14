import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminUserIsActiveToggleComponent } from './admin-user-is-active-toggle-component';

describe('AdminUserIsActiveToggleComponent', () => {
  let component: AdminUserIsActiveToggleComponent;
  let fixture: ComponentFixture<AdminUserIsActiveToggleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminUserIsActiveToggleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminUserIsActiveToggleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

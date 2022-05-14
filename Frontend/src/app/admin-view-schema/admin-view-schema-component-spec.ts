import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminViewSchemaComponent } from './admin-view-schema-component';

describe('AdminUserIsActiveToggleComponent', () => {
  let component: AdminViewSchemaComponent;
  let fixture: ComponentFixture<AdminViewSchemaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminViewSchemaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminViewSchemaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

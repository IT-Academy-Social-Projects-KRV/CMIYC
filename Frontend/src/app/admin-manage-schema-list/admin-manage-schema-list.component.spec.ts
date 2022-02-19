import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminManageSchemaListComponent } from './admin-manage-schema-list.component';

describe('AdminManageSchemaListComponent', () => {
  let component: AdminManageSchemaListComponent;
  let fixture: ComponentFixture<AdminManageSchemaListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminManageSchemaListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminManageSchemaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

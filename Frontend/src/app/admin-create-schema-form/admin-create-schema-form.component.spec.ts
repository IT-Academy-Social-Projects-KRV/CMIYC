import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminCreateSchemaFormComponent } from './admin-create-schema-form.component';

describe('AdminCreateSchemaFormComponent', () => {
  let component: AdminCreateSchemaFormComponent;
  let fixture: ComponentFixture<AdminCreateSchemaFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminCreateSchemaFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminCreateSchemaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

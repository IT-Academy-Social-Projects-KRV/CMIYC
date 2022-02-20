import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserSearchInterfaceComponent } from './user-search-interface.component';

describe('UserSearchInterfaceComponent', () => {
  let component: UserSearchInterfaceComponent;
  let fixture: ComponentFixture<UserSearchInterfaceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserSearchInterfaceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserSearchInterfaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

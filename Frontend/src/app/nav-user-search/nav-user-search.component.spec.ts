import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavUserSearchComponent } from './nav-user-search.component';

describe('NavUserSearchComponent', () => {
  let component: NavUserSearchComponent;
  let fixture: ComponentFixture<NavUserSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavUserSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavUserSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchResponseComponent } from './search-response.component';

describe('SearchResponseComponent', () => {
  let component: SearchResponseComponent;
  let fixture: ComponentFixture<SearchResponseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchResponseComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchResponseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

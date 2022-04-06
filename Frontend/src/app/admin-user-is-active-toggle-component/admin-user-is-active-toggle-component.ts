import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'user-is-active-toggle',
  templateUrl: './admin-user-is-active-toggle-component.html',
  styleUrls: ['./admin-user-is-active-toggle-component.css']
})
export class AdminUserIsActiveToggleComponent implements OnInit {

  @Input()
  userId: number = -1;

  @Input()
  isActive: boolean = false;

  @Input()
  isDisabled: boolean = false;

  @Output()
  onChange = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit(): void {
  }

  toggleIsActive() {
    this.isActive = !this.isActive;
    this.onChange.emit(this.isActive);
  }
}

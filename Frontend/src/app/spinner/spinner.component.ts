import {Component, HostListener, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.css']
})
export class SpinnerComponent implements OnInit {

  getScreenHeight: number = 0;
  _headerHeight: number = 0;

  @Input()
  set headerHeight(param: number) {
    this._headerHeight = param;
    console.log(this._headerHeight);
  }

  constructor() { }

  ngOnInit(): void {
    this.onWindowResize();
  }

  @HostListener('window:resize', ['$event'])
  onWindowResize() {
    this.getScreenHeight = window.innerHeight;
  }
}

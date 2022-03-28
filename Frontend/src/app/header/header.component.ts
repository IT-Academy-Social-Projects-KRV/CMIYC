import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userName: string | null;
  constructor() {
    this.userName = localStorage.getItem('fullName');
  }

  ngOnInit(): void {
  }

}

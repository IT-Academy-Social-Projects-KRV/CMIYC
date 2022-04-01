import {Component, OnInit} from '@angular/core';
import {AuthService} from "../shared/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userName: string | null;
  constructor(private authService: AuthService) {
    this.userName = localStorage.getItem('full_name');
  }

  ngOnInit(): void {
  }
  isLogout() {
    console.log("logout work");
    this.authService.performLogout();
    }
}

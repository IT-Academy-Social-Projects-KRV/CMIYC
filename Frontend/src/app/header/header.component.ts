import {Component, OnInit} from '@angular/core';
import {AuthService} from "../shared/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userName: string | null;
  isSchemaAdmin: boolean;
  isUserAdmin: boolean;

  constructor(private authService: AuthService) {
    this.userName = authService.getCurrentUserFullName();
    this.isSchemaAdmin = authService.isSchemaAdminLoggedIn();
    this.isUserAdmin = authService.isUserAdminLoggedIn();
  }

  ngOnInit(): void {
  }
  isLogout() {
    this.authService.performLogout();
  }
}

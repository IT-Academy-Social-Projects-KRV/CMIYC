import { Component, OnInit } from '@angular/core';
import { AuthService } from '../shared/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  readonly isUserAdmin: boolean;
  readonly isSchemaAdmin: boolean;
  readonly isUser: boolean;

  constructor(private authService: AuthService ) {
    this.isUserAdmin = authService.isUserAdminLoggedIn();
    this.isSchemaAdmin = authService.isSchemaAdminLoggedIn();
    this.isUser = authService.isUserLoggedIn();
  }

  ngOnInit(): void {
  }
  isLogout() {
    this.authService.performLogout();
    }
}

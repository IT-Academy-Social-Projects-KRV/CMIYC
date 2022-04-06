import { Component, OnInit } from '@angular/core';
import {HttpClientService} from "../shared/http.client.service";
import {User} from "../shared/data/user";

@Component({
  selector: 'app-admin-manage-user-list',
  templateUrl: './admin-manage-user-list.component.html',
  styleUrls: ['./admin-manage-user-list.component.css']
})
export class AdminManageUserListComponent implements OnInit {

  users: User[] = [];

  constructor(private httpClientService: HttpClientService) { }

  ngOnInit(): void {
    this.httpClientService
      .getUsers()
      .subscribe({
        next: (usersFromServer) => {
          this.loadUsers(usersFromServer);
        },

        // TODO: remove this after auth will implement /users endpoint
        error: () => {
          const mockData = [
            {"id":1,"email":"user@gmail.com","firstName":"User","lastName":"Foo","registerDate":"2022-03-28","scopes":["user"],"active":true},
            {"id":2,"email":"admin_u@gmail.com","firstName":"Admin","lastName":"User","registerDate":"2022-03-28","scopes":["admin_user"],"active":true},
            {"id":3,"email":"admin_s@gmail.com","firstName":"Admin","lastName":"Schema","registerDate":"2022-03-28","scopes":["admin_schema"],"active":true}
          ];

          // @ts-ignore
          this.loadUsers(mockData);
        }
      })
  }

  private loadUsers(users: User[]) {
    for (let i = 0; i < users.length; i++) {
      const u = users[i];
      this.users.push(
        new User(
          u.id, u.email, u.firstName, u.lastName,
          u.active, u.registerDate, u.scopes
        )
      );
    }
  }

  onUserActiveChange(userId: number, isActive: boolean): void {
    const user: User | undefined = this.users.filter(value => value.id == userId)[0];
    if(user && user.active != isActive) {
      this
        .httpClientService
        .setUserActive(userId, isActive)
        .subscribe(() => {
          user.active = isActive;
        })
    }
  }
}

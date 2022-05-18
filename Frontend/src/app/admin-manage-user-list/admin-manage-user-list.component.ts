import { Component, OnInit } from '@angular/core';
import {HttpClientService} from "../shared/http.client.service";
import {User} from "../shared/data/user";

@Component({
  selector: 'app-admin-manage-user-list',
  templateUrl: './admin-manage-user-list.component.html',
  styleUrls: ['./admin-manage-user-list.component.css']
})
export class AdminManageUserListComponent implements OnInit {

  RESPONSE:any;
  page = 1;
  count:number|undefined;
  tableSize=10;
  //tableSizes = [1, 3, 6, 9]; variable used for items per page selection
  users: User[] = [];

  constructor(private httpClientService: HttpClientService) {

  }

  ngOnInit(): void {

    this.httpClientService.getInitialPage(this.tableSize).subscribe({
      next:(response)=>{
        this.RESPONSE=response;
        this.count=this.RESPONSE.totalElements;
        const usersList = this.RESPONSE.content;
        this.loadUsers(usersList);
      }
    })

  }

  private loadUsers(users: User[]) {
    this.users=[];
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

  onTableDataChange(event:any){
    this.users=[];
    this.page=event;
    this.httpClientService.getUsersOnPage(event,this.tableSize).subscribe({
      next:(response)=>{
        this.RESPONSE=response;
        const usersList = this.RESPONSE.content;
        this.loadUsers(usersList);
      }
    })

  }

/*  method for items per page selection
  onTableSizeChange(event:any): void {
    this.tableSize = event.target.value;
    this.page = 1;
   // use method for display items
  }*/

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

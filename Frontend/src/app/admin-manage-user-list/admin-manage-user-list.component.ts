import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../shared/http.client.service";
import {User} from "../shared/data/user";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-admin-manage-user-list',
  templateUrl: './admin-manage-user-list.component.html',
  styleUrls: ['./admin-manage-user-list.component.css']
})
export class AdminManageUserListComponent implements OnInit {
  idToUpdate: number | undefined;
  firstNameToUpdate: string | undefined;
  lastNameToUpdate: string | undefined;
  emailToUpdate: string | undefined;
  isUserAdminToUpdate: boolean | undefined;
  isSchemaAdminToUpdate: boolean | undefined;
  isUserToUpdate: boolean | undefined;
  RESPONSE: any;
  page = 1;
  count: number | undefined;
  tableSize = 8;
  //tableSizes = [1, 3, 6, 9]; variable used for items per page selection
  users: User[] = [];
  spinner: boolean = false;

  form: FormGroup = new FormGroup({
    firstNameSearch: new FormControl(''),
    lastNameSearch: new FormControl(''),
    emailSearch: new FormControl(''),
    isActiveSearch: new FormControl('')
  });

  constructor(private httpClientService: HttpClientService) {
  }

  onSubmit(): void {
    this.page = 1;
    const firstName = this.form.controls['firstNameSearch'].value;
    const lastName = this.form.controls['lastNameSearch'].value;
    const email = this.form.controls['emailSearch'].value;
    const isActive = this.form.controls['isActiveSearch'].value == true;

    this.httpClientService.getUsersSearchInitialPage(this.tableSize, firstName, lastName, email, isActive).subscribe({
      next: (response) => {
        this.RESPONSE = response;
        this.count = this.RESPONSE.totalElements;
        const usersList = this.RESPONSE.content;
        this.loadUsers(usersList);
      }
    })

  }

  getAllUsersClearSearchFields(): void {
    this.form.controls['firstNameSearch'].setValue('');
    this.form.controls['lastNameSearch'].setValue('');
    this.form.controls['emailSearch'].setValue('');

    this.ngOnInit();
  }


  ngOnInit(): void {

    this.spinner = true;

    this.httpClientService.getInitialPage(this.tableSize).subscribe({
      next: (response) => {
        this.RESPONSE = response;
        this.count = this.RESPONSE.totalElements;
        const usersList = this.RESPONSE.content;
        this.loadUsers(usersList);
        this.spinner = false;
      }
    })

  }

  private loadUsers(users: User[]) {
    this.users = [];
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

  onTableDataChange(event: any) {
    this.users = [];
    this.page = event;

    const firstName = this.form.controls['firstNameSearch'].value;
    const lastName = this.form.controls['lastNameSearch'].value;
    const email = this.form.controls['emailSearch'].value;
    const isActive = this.form.controls['isActiveSearch'].value == true;

    if ((firstName == null || firstName == "") && (lastName == null || lastName == "") && (email == null || email == "")){
        this.httpClientService.getUsersOnPage(event, this.tableSize).subscribe({
          next: (response) => {
            this.RESPONSE = response;
            const usersList = this.RESPONSE.content;
            this.loadUsers(usersList);
            this.spinner = false;
          }
        })
      } else {
        this.httpClientService.getUsersSearchOnPage(event, this.tableSize, firstName, lastName, email, isActive).subscribe({
          next: (response) => {
            this.RESPONSE = response;
            const usersList = this.RESPONSE.content;
            this.loadUsers(usersList);
            this.spinner = false;
          }
        })
      }
  }

  onDelete(userId: number, userName: string, userLastName: string) {
    let message = "Are you sure you want to delete \"" + userName + " " + userLastName + "\" profile?\n" +
      "\n" +
      "All information associated to this user profile will be permanently deleted. " +
      "This operation can not be undone.";
    if (confirm(message)) {
      this.httpClientService.deleteUser(userId).subscribe(list => {
        this.loadUsers(this.users);
        this.spinner = false;
      })
      window.location.reload();
    }

  }

  onEdit(id: number, firstName: string, lastName: string, email: string, isUserAdmin: boolean, isSchemaAdmin: boolean, isUser: boolean) {
    this.idToUpdate = id;
    this.firstNameToUpdate = firstName;
    this.lastNameToUpdate = lastName;
    this.emailToUpdate = email;
    this.isUserAdminToUpdate = isUserAdmin;
    this.isSchemaAdminToUpdate = isSchemaAdmin;
    this.isUserToUpdate = isUser;
  }

  /*  method for items per page selection
    onTableSizeChange(event:any): void {
      this.tableSize = event.target.value;
      this.page = 1;
     // use method for display items
    }*/

  onUserActiveChange(userId: number, isActive: boolean): void {
    const user: User | undefined = this.users.filter(value => value.id == userId)[0];
    if (user && user.active != isActive) {
      this
        .httpClientService
        .setUserActive(userId, isActive)
        .subscribe(() => {
          user.active = isActive;
        })
    }
  }
}

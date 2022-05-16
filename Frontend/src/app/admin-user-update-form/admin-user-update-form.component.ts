import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClientService} from "../shared/http.client.service";

@Component({
  selector: 'app-admin-user-update-form',
  templateUrl: './admin-user-update-form.component.html',
  styleUrls: ['./admin-user-update-form.component.css']
})
export class AdminUserUpdateFormComponent implements OnInit {
  @Input() firstNameToForm: any;
  @Input() lastNameToForm: any;
  @Input() emailToForm: any;
  @Input() isUserAdminToForm: any;
  @Input() isSchemaAdminToForm: any;
  @Input() isUserToForm: any;
  form: FormGroup = new FormGroup({
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    userManager: new FormControl(''),
    schemaManager: new FormControl(''),
    user: new FormControl(''),
    email: new FormControl('')

  });
  constructor(private httpClientService: HttpClientService) {

  }

  ngOnInit(): void {

  }

  onSubmit(){}
}

import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClientService} from "../shared/http.client.service";

@Component({
  selector: 'app-admin-user-update-form',
  templateUrl: './admin-user-update-form.component.html',
  styleUrls: ['./admin-user-update-form.component.css']
})
export class AdminUserUpdateFormComponent implements OnInit {
  @Input() idToForm: any;
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
  constructor(private httpClientService: HttpClientService,private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      firstName: [this.firstNameToForm],
      lastName: [this.lastNameToForm],
      userManager:[this.isUserAdminToForm],
      schemaManager:[this.isSchemaAdminToForm],
      user:[this.isUserToForm],
      email:[this.emailToForm]
    });
  }

  onSubmit(){
    let isUserManager;
    let isSchemaManager;
    let isUser;
    if(this.form.controls['userManager'].value==null){
      isUserManager=this.isUserAdminToForm;
    }else{
      isUserManager=this.form.controls['userManager'].value;
    }
    if(this.form.controls['schemaManager'].value==null){
      isSchemaManager=this.isSchemaAdminToForm;
    }else{
      isSchemaManager=this.form.controls['schemaManager'].value;
    }
    if(this.form.controls['user'].value==null){
      isUser=this.isUserToForm;
    }else{
      isUser=this.form.controls['user'].value;
    }

    let roles: (string | null)[] = [
      isUserManager ? 'admin_user' : null,
      isSchemaManager ? 'admin_schema' : null,
      isUser ? 'user' : null,
  ].filter(x => x != null);

    const data = {
      "firstName": this.form.controls['firstName'].value,
      "lastName" : this.form.controls['lastName'].value,
      "email": this.form.controls['email'].value,
      "roles": roles
    }

    this.httpClientService.updateUser(this.idToForm, data, (result) => {
      if (result.isError) {
        alert(result.message)
      } else {
        window.location.reload();
      }
    })
  }
}

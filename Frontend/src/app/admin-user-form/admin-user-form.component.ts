import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validator, Validators} from "@angular/forms";
import {HttpClientService} from "../shared/http.client.service";
import Validation from "../utils/validation";


@Component({
  selector: 'app-admin-user-form',
  templateUrl: './admin-user-form.component.html',
  styleUrls: ['./admin-user-form.component.css']
})
export class AdminUserFormComponent implements OnInit {
  form: FormGroup = this.formBuilder.group({
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    userManager: new FormControl(''),
    schemaManager: new FormControl(''),
    user: new FormControl(''),
    email: new FormControl('')
  });
  spinner: boolean = false;

  constructor(private httpClientService: HttpClientService, private formBuilder: FormBuilder) {
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      firstName: ['', [Validators.minLength(2), Validators.maxLength(20)]],
      lastName: ['', [Validators.minLength(2), Validators.maxLength(20)]],
      userManager:[''],
      schemaManager:[''],
      user:[''],
      email:['', [Validators.email]]
    });
  }

  onSubmit(): void {

    this.spinner = true;

    if (this.form.invalid) {
      return;
    }
    const firstName = this.form.controls['firstName'].value;
    const lastName = this.form.controls['lastName'].value;
    const email = this.form.controls['email'].value;

    const userManager = this.form.controls['userManager'].value == true;
    const schemaManager = this.form.controls['schemaManager'].value == true;
    const user = this.form.controls['user'].value == true;

    const roles: (string | null)[] = [
      userManager ? 'admin_user' : null,
      schemaManager ? 'admin_schema' : null,
      user ? 'user' : null,
    ].filter(x => x != null);

    this.httpClientService.createUser(firstName, lastName, email, roles, (result) => {
      if (result.isError) {
        alert(result.message)
        this.spinner = false;
      } else {
        window.location.reload();
      }
    })
  }


}

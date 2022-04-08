import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import Validation from '../utils/validation';
import {Router} from "@angular/router";
import {router} from "ngx-bootstrap-icons";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  readonly form: FormGroup;
  readonly email: string;

  submitted = false;

  constructor(private formBuilder: FormBuilder, private router: Router) {
    const currentNavigation = this.router.getCurrentNavigation();
    this.email = currentNavigation?.finalUrl?.queryParams["email"];
    if (this.email == undefined || !this.email.match("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")) {
      this.router.navigate(['login'])
    }

    this.form = this.formBuilder.group(
      {
        password: ['', Validation.passwordValidator()],
        confirmPassword: ['', Validators.required],
        token: ['', Validation.uuid()]
      },
      {
        validators: [Validation.match('password', 'confirmPassword')]
      }
    );
  }

  ngOnInit(): void {}

  get password(): AbstractControl {
    return this.form.controls['password'];
  }

  get passwordConfirm(): AbstractControl {
    return this.form.controls['password'];
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    // console.log(JSON.stringify(this.form.value, null, 2));
  }

}

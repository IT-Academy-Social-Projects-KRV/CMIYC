import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import Validation from '../utils/validation';
import {Router} from "@angular/router";
import {HttpClientService} from "../shared/http.client.service";
import {RequestResult} from "../shared/data/request-result";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  readonly form: FormGroup;
  readonly token: string;

  submitted = false;
  errorMessage: string | undefined;

  constructor(private formBuilder: FormBuilder, private router: Router, private httpClientService: HttpClientService) {
    const segments = document.location.pathname.split('/');
    if (segments.length < 3) {
      this.router.navigate(['login'])
    }

    this.token = segments[segments.length - 1];
    this.form = this.formBuilder.group(
      {
        password: ['', Validation.passwordValidator()],
        confirmPassword: [''],
        token: ['']
      },
      {
        validators: [Validation.match('password', 'confirmPassword')]
      }
    );
  }

  ngOnInit(): void {
  }

  get password(): AbstractControl {
    return this.form.controls['password'];
  }

  get confirmPassword(): AbstractControl {
    return this.form.controls['confirmPassword'];
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.form.invalid) {
      return;
    }

    this.httpClientService
      .activateUser(
        this.token, this.password.value, this.confirmPassword.value,
        (result: RequestResult) => {
          if(!result.isError) {
            this.router.navigate(['login'], {queryParams: {'activationSuccess': ''}});
          } else {
            this.errorMessage = result.message;
            console.log(this.errorMessage)
          }
        });
  }

}

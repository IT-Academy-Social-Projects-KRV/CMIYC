import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from "../shared/auth.service";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  readonly showSuccessMessage: boolean;

  form: FormGroup = new FormGroup({
    email: new FormControl(''),
    password: new FormControl('')
  });

  submitted: boolean = false;
  errorMessage: string | undefined;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService,
  ) {
    const showSuccessMessage: boolean | undefined = this.router
      .getCurrentNavigation()
      ?.finalUrl
      ?.queryParamMap
      .has("activationSuccess");

    this.showSuccessMessage = showSuccessMessage == true;
    /*this.showSuccessMessage=true;*/
  }

  readonly resetErrorMessage = () => {
    if(!this.errorMessage) return;
    console.log('reset')
    this.errorMessage = undefined;
    this.form.controls['password'].updateValueAndValidity();
  }

  private readonly showErrorValidator = (): ValidationErrors | null => {
    return this.errorMessage ? {errorMessage: true} : null;
  };

  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, this.showErrorValidator]],
      }
    );
  }

  get controls(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }

    let email = this.form.get('email')?.value;
    let password = this.form.get('password')?.value;

    this.authService.performLogin(
      email, password,
      (errorMessage: string) => {
        this.errorMessage = errorMessage;
        this.form.controls['password'].updateValueAndValidity();
      }
    );
  }
}

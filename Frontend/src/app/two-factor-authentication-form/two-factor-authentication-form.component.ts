import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {Router} from "@angular/router";
import {AuthService} from "../shared/auth.service";

@Component({
  selector: 'app-two-factor-authentication-form',
  templateUrl: './two-factor-authentication-form.component.html',
  styleUrls: ['./two-factor-authentication-form.component.css']
})
export class TwoFactorAuthenticationFormComponent implements OnInit {

  form: FormGroup = new FormGroup({
    code: new FormControl('')
  });
  submitted = false;
  errorMessage: string | undefined;

  constructor(private formBuilder: FormBuilder,private router: Router,
              private authService: AuthService) { }

  readonly resetErrorMessage = () => {
    if(!this.errorMessage) return;
    console.log('reset')
    this.errorMessage = undefined;
    this.form.controls['code'].updateValueAndValidity();
  }

  private readonly showErrorValidator = (): ValidationErrors | null => {
    return this.errorMessage ? {errorMessage: true} : null;
  };

  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        code: [
          '',
          [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(6),
            Validators.pattern('^[0-9]*$'),
            this.showErrorValidator
          ]
        ]
      }
    );
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }
  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }

    let code = this.form.get('code')?.value;

    this.authService.performSecondFactor(
      code,
      (errorMessage: string) => {
        this.errorMessage = errorMessage;
        this.form.controls['code'].updateValueAndValidity();
      }
    );
    // console.log(JSON.stringify(this.form.value, null, 2));
  }

  isLogout() {
    this.authService.performLogout();
  }
}

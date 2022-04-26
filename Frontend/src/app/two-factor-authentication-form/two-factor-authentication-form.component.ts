import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {Router} from "@angular/router";
import {AuthService} from "../shared/auth.service";

@Component({
  selector: 'app-two-factor-authentication-form',
  templateUrl: './two-factor-authentication-form.component.html',
  styleUrls: ['./two-factor-authentication-form.component.css']
})
export class TwoFactorAuthenticationFormComponent implements OnInit {
  errorMessage: string | undefined;
  form: FormGroup = new FormGroup({
    code: new FormControl('')
  });
  submitted = false;
  constructor(private formBuilder: FormBuilder,private router: Router,
              private authService: AuthService,) { }
  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        code: [
          '',
          [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(6),
            Validators.pattern('^[0-9]*$')
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

}

import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

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
  constructor(private formBuilder: FormBuilder) {}
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
    console.log(JSON.stringify(this.form.value, null, 2));
  }

}

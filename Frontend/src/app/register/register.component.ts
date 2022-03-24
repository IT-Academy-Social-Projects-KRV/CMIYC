import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import Validation from '../utils/validation';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: FormGroup = new FormGroup({
    password: new FormControl(''),
    confirmPassword: new FormControl('')
  });
  submitted = false;
  constructor(private formBuilder: FormBuilder) { }
  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        password: ['', Validation.passwordValidator()],
        confirmPassword: ['', Validators.required]
      },
      {
        validators: [Validation.match('password', 'confirmPassword')]
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
    // console.log(JSON.stringify(this.form.value, null, 2));
  }

}

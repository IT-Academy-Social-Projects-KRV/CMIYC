import { ClassField } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Validation from '../utils/validation';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  form: FormGroup = new FormGroup({
    email: new FormControl(''),
    password: new FormControl('')
  });
  submitted = false;
  constructor(private formBuilder: FormBuilder, private router: Router) { }
  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        password: ['', Validators.required],
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
  }

}

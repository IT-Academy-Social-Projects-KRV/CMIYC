import { ClassField } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Validation from '../utils/validation';
import {AuthService} from "../shared/auth.service";
import {HttpClient} from "@angular/common/http";
import { repos } from '../repos';


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
  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private authService: AuthService,
              private http: HttpClient) { }
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
    let email = this.form.get('email')?.value;
    let password = this.form.get('password')?.value;

    this.authService.login<repos>(email, password).subscribe(response => {
      console.log(response.access_token);
      localStorage.setItem('access_token', response.access_token);
      localStorage.setItem('scope', response.scope);
      if(localStorage.getItem('scope') =='user'){
      this.router.navigate(['userSearch']);
      }else{
      this.router.navigate(['admin/allUsers']);
      }
    });
}

}

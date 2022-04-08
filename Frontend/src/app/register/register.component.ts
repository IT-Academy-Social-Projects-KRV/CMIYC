import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import Validation from '../utils/validation';
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  readonly form: FormGroup;
  readonly email: string;

  submitted = false;
  tokenIsValid = false;

  private readonly uuidValidator = (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    this.tokenIsValid = false;
    if(!value.length) return null;

    if(value.match('^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$')) {
      this.tokenIsValid = true;
      return null;
    }

    return {uuid: true};
  };

  constructor(private formBuilder: FormBuilder, private router: Router) {
    const currentNavigation = this.router.getCurrentNavigation();
    this.email = currentNavigation?.finalUrl?.queryParams["email"];
    if (this.email == undefined || !this.email.match("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")) {
      this.router.navigate(['login'])
    }

    this.form = this.formBuilder.group(
      {
        token: ['', this.uuidValidator],
        password: ['', Validation.passwordValidator()],
        confirmPassword: ['']
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

  get confirmPassword(): AbstractControl {
    return this.form.controls['confirmPassword'];
  }

  get token(): AbstractControl {
    return this.form.controls['token'];
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    console.log('submit')
  }

}

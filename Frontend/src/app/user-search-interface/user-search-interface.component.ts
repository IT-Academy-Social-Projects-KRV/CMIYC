import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import Validation from '../utils/validation';

@Component({
  selector: 'app-user-search-interface',
  templateUrl: './user-search-interface.component.html',
  styleUrls: ['./user-search-interface.component.css']
})
export class UserSearchInterfaceComponent implements OnInit {
  form: FormGroup = new FormGroup({
    email: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    birthDate: new FormControl(''),
    sex: new FormControl(''),
    api1: new FormControl(''),
    api2: new FormControl(''),
    api3: new FormControl('')
  });
  //dt: Date = new Date();
  submitted = false;
  constructor(private formBuilder: FormBuilder) { }
  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        email: ['', Validators.email],
        firstName: ['', Validation.nameValidator()],
        lastName: ['', Validation.nameValidator()],
        birthDate: [''],
        sex: [''],
        api1: [false],
        api2: [false],
        api3: [false]
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

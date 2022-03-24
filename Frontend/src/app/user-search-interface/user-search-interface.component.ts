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
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    birthDayDate: new FormControl(''),
    sex: new FormControl('')
  });
  apis: FormGroup = new FormGroup({
    api1: new FormControl(''),
    api2: new FormControl(''),
    api3: new FormControl('')
  });
  submitted = false;
  constructor(private formBuilder: FormBuilder) { }
  ngOnInit(): void {
    this.form = this.formBuilder.group(
      {
        firstName: ['', Validation.nameValidator()],
        lastName: ['', Validation.nameValidator()],
        birthDayDate: [''],
        sex: ['']
      }
    );
    this.apis = this.formBuilder.group(
      {
        api1: [false],
        api2: [false],
        api3: [false]
      }
    )
  }

  get f(): { [key: string]: AbstractControl } {
    return this.form.controls;
  }
  onSubmit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      return;
    }
    this.form.value['foreignDataSource'] = this.getAPIs();
  }

  getAPIs(): string[] {
    let apis: string[] = [];
    let index: number = 0;
    for (let i = 0; i < Object.values(this.apis.value).length; i++) {
      if (Object.values(this.apis.value)[i] == true) {
        apis[index] = Object.keys(this.apis.value)[i];
        index++;
      }
    }
    return apis;
  }

}

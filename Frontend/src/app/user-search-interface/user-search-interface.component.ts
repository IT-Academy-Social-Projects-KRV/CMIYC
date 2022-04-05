import { AuthService } from './../shared/auth.service';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import Validation from '../utils/validation';
import {HttpClientService} from "../shared/http.client.service";

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
  constructor(private formBuilder: FormBuilder, private httpClientService: HttpClientService) { }
  ngOnInit(): void {
    this.httpClientService.getSchemas().subscribe(response => console.log(response));
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
    this.httpClientService.search(this.form).subscribe({
      next: response => {
        console.log(response);
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });
  }

  getAPIs(): (string | null)[] {
    return Object.keys(this.apis.value)
      .flatMap(key => this.apis.get(key)?.value ? key : key = '')
      .filter((a) => a);
  }

  onTextChange(): void {
    // this method will be change
    this.apis.get('api1')?.setValue(this.form.get('firstName')?.value.length > 0);
    this.apis.get('api2')?.setValue(this.form.get('lastName')?.value.length > 0);
    this.apis.get('api3')?.setValue(this.form.get('birthDayDate')?.value.length > 0);
  }
}

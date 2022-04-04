import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../shared/http.client.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-admin-create-schema-form',
  templateUrl: './admin-create-schema-form.component.html',
  styleUrls: ['./admin-create-schema-form.component.css']
})
export class AdminCreateSchemaFormComponent implements OnInit {

  form = new FormGroup({
    file: new FormControl('', [Validators.required]),
    fileSource: new FormControl('', [Validators.required])
  });


  onFileChange(event: Event) {

    // @ts-ignore
    if (event.target.files.length > 0) {
      // @ts-ignore
      const file = event.target.files[0];
      this.form.patchValue({
        fileSource: file
      });
    }
  }


  constructor(private httpclient: HttpClientService, private formBuilder: FormBuilder) {
  }


  ngOnInit(): void {
  }

  onFormSubmit() {
    const formData = new FormData();

    // @ts-ignore
    formData.append('file', this.form.get('fileSource').value);


    const upload$ = this.httpclient.sendSchema(formData);

    upload$.subscribe({
      next: value => {
        alert("success")
      },
      error: err => {
        const errorMessage: string = err.message || "Something went wrong";
        alert(errorMessage);
      }
    });
  }


}


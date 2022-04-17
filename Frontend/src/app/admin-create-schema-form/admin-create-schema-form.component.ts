import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../shared/http.client.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-admin-create-schema-form',
  templateUrl: './admin-create-schema-form.component.html',
  styleUrls: ['./admin-create-schema-form.component.css']
})
export class AdminCreateSchemaFormComponent implements OnInit {

  submitted = false;

 form = new FormGroup({
    file: new FormControl('', [Validators.required]),
    fileSource: new FormControl('', [Validators.required])
  });

  get f () { return this.form.controls }

  onFileChange($event: any) {

    if ($event.target.files && $event.target.files[0]) {
      let file = $event.target.files[0];
      let allowedSize = file.size<=500000;
      console.log(file);
      if(allowedSize){
        console.log("correct");
      }
      else {
        this.form.reset();
        this.form.controls["file"].setValidators([Validators.required]);
        // @ts-ignore
        this.form.get('file').updateValueAndValidity();
      }
    }
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
    this.form = this.formBuilder.group({
      file: ['', [Validators.required]],
      fileSource: ['', [Validators.required]]
    });
  }

  onFormSubmit() {
    this.submitted = true;

    if (this.form.invalid) {
      return;
    }
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


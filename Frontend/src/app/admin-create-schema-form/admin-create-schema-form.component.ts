import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../shared/http.client.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-admin-create-schema-form',
  templateUrl: './admin-create-schema-form.component.html',
  styleUrls: ['./admin-create-schema-form.component.css']
})
export class AdminCreateSchemaFormComponent implements OnInit {

  errorMessage: boolean | undefined;
  fileName: string | undefined;
  submitted = false;
  maxSize = 500000;
  spinner: boolean = false;

 form = new FormGroup({
    file: new FormControl('', ),
    fileSource: new FormControl('', )
  });

  get f ()  { return this.form.controls }

  onFileChange($event: any) {
    this.errorMessage=false;
    if ($event.target.files && $event.target.files[0]) {
      let file = $event.target.files[0];
      let size = file.size;
      let type = file.type == "text/" +
        "xml"
      this.fileName = file.name;
      console.log(file);
      if(size<this.maxSize&&type){
        console.log("file is acceptable")
      }
      else {
        console.log("file is not acceptable")
        this.errorMessage = true;
        this.form.reset();
        this.form.controls['file'].updateValueAndValidity();
      }
    }
    // @ts-ignore
    if ($event.target.files.length > 0) {
      // @ts-ignore
      const file = $event.target.files[0];
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
    this.spinner = true;

    if (this.form.invalid) {
      return;
    }

    const formData = new FormData();

    // @ts-ignore
    formData.append('file', this.form.get('fileSource').value);

    const upload$ = this.httpclient.uploadSchema(formData);

    upload$.subscribe({
      next: value => {
        document.location.reload();
      },
      error: err => {
        const errorMessage: string = err.error || "Something went wrong";
        alert(errorMessage);
        this.spinner = false;
      }
    });
  }
}


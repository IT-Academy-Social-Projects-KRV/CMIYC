import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidatorFn} from "@angular/forms";
import {Field, FieldType} from "../shared/data/interface-schema";
import Validation from "../utils/validation";

@Component({
  selector: 'json-form',
  templateUrl: './json-form-component.html',
  styleUrls: ['./json-form-component.css']
})
export class JsonForm implements OnChanges {

  @Input()
  fields: Field[] = [];

  form: FormGroup = new FormGroup({});
  submitted = false;

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    const controls: any = {};
    this.fields.forEach(field => {
      if(field.name) {
        const control = new FormControl();
        control.addValidators(getValidators(field))

        controls[field.name] = control;
      }
    });
    this.form = new FormGroup(controls);

    console.log(this.form)
  }

  getValidators(field: Field): ValidatorFn[] {
    const result: ValidatorFn[] = [];

    switch (field.type) {
      case FieldType.Alphanumeric:
        result.push(Validation.alphanumericValidator);
        break;
      case FieldType.Alphabetic:
        result.push(Validation.alphabeticValidator);
        break;
      case FieldType.Numeric:
        result.push(Validation.numericValidator);
        break;
    }

    return result;
  }

  onSubmit(): void {
    this.submitted = true;
  }

  normalizeCamelCase(str: string): string {
    return str.replace(/([A-Z])/g, ' $1');
  }
}

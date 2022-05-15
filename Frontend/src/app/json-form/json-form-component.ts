import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn} from "@angular/forms";
import {Field, FieldComponent, FieldType} from "../shared/data/interface-schema";
import Validation from "../utils/validation";
import {SelectDataset} from "../shared/data/select-data";

@Component({
  selector: 'json-form',
  templateUrl: './json-form-component.html',
  styleUrls: ['./json-form-component.css']
})
export class JsonForm implements OnChanges {

  @Input()
  fields: Field[] = [];
  fieldData: InputData[] = [];

  form: FormGroup = new FormGroup({});
  submitted = false;

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    const controls: any = {};
    this.fieldData = [];
    this.fields.forEach(field => {
      this.fieldData.push(InputData.fromField(field));

      if(field.name) {
        const control = new FormControl();
        control.addValidators(this.getValidators(field))
        control.setValue("");

        controls[field.name] = control;
      }
    });

    console.log(this.fieldData)
    this.form = new FormGroup(controls);
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

    console.log(this.form.valid)
    for (let controlsKey in this.form.controls) {
      const control = this.form.controls[controlsKey]
      console.log(controlsKey, control.value)
    }
  }

  getErrors(field: Field): string[] {
    const fieldErrors = this.form.controls[field.name].errors;
    const errors = []

    for (let errorsKey in fieldErrors) {
      errors.push(this.form.controls[field.name].getError(errorsKey))
    }

    return errors;
  }
}

class InputData {
  readonly normalizedName: string;
  readonly inputType: string;
  readonly dataset: SelectDataset | undefined;
  readonly field: Field;
  readonly component: FieldComponent;
  readonly components: InputData[] = [];

  private constructor(
      name: string, inputType: string, dataset: SelectDataset | undefined,
      field: Field, component: FieldComponent, components: InputData[]
  ) {
    this.normalizedName = name;
    this.inputType = inputType;
    this.dataset = dataset;
    this.field = field;
    this.component = component;
    this.components = components;
  }

  static fromComponent(component: FieldComponent): InputData {
    return new InputData(
      InputData.normalizeCamelCase(component.name),
      InputData.getInputTypeFromFieldType(component.type),
      undefined,
      new Field(),
      component,
      []
    );
  }

  static fromField(field: Field): InputData {
    const components: InputData[] = field.components ?
      field.components.map(comp => InputData.fromComponent(comp)) : [];

    const dataset: SelectDataset | undefined = SelectDataset.getDatasetByName(field.name);

    return new InputData(
      InputData.normalizeCamelCase(field.name),
      InputData.getInputTypeFromFieldType(field.type),
      dataset,
      field,
      new FieldComponent(),
      components
    );
  }

  static normalizeCamelCase(str: string): string {
    return str.replace(/([A-Z])/g, ' $1');
  }

  static getInputTypeFromFieldType(type: FieldType | undefined): string {
    switch (type) {
      case FieldType.Numeric:
      case FieldType.Date:
        return "number";
      default:
        return "text";
    }
  }
}

import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn} from "@angular/forms";
import {Field, FieldCombination, FieldComponent, FieldType} from "../shared/data/interface-schema";
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

  @Input()
  combinations: FieldCombination[] = [];

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
        if(field.components) {
          field.components.forEach(component => {
            const control = new FormControl();

            control.addValidators(this.getValidators(component.type))
            control.setValue("");

            controls[field.name + "." + component.name] = control;
          });
        } else {
          const control = new FormControl();

          control.addValidators(this.getValidators(field.type))
          control.setValue("");

          controls[field.name] = control;
        }
      }
    });

    console.log(this.fieldData);
    this.form = new FormGroup(controls);
  }

  getValidators(type: FieldType | undefined): ValidatorFn[] {
    const result: ValidatorFn[] = [];

    switch (type) {
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

    if(!this.form.valid)
      return;

    const data: any = {};
    this.fields.forEach(field => {
      const key = field.name;
      const control = this.form.controls[key];
      if(control) {
        if(control.value) {
          data[key] = control.value;
        }
      } else {
        const complexInputData: any = {};
        let anyComponentHasValue = false;
        field.components.forEach(comp => {
          const componentInput = this.form.controls[key + "." + comp.name];
          if(componentInput) {
            complexInputData[comp.name] = componentInput.value;
            if(componentInput.value) {
              anyComponentHasValue = true;
            }
          }
        });

        if(anyComponentHasValue)
          data[key] = complexInputData;
      }
    });

    console.log(data);

  }

  getAllErrors(field: Field): string[] {
    const errors: string[] = [];
    const name = field.name;

    for (let controlName in this.form.controls) {
      if(controlName == name || controlName.startsWith(name + ".")) {
        const control = this.form.controls[controlName];
        const fieldErrors = control.errors;
        for (let errorsKey in fieldErrors) {
          errors.push(control.getError(errorsKey));
        }
      }
    }

    return errors.filter((v, i, a) => a.indexOf(v) === i);
  }

  getErrors(name: string): string[] {
    const errors: string[] = [];

    for (let controlName in this.form.controls) {
      if(controlName == name || controlName.endsWith("." + name)) {
        const control = this.form.controls[controlName];
        const fieldErrors = control.errors;
        for (let errorsKey in fieldErrors) {
          errors.push(control.getError(errorsKey));
        }
      }
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

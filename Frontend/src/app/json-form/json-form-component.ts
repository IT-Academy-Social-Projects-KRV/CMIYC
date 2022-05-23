import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, ValidatorFn} from "@angular/forms";
import {ApiCombination, FieldType, JsonForm, JsonFormInput} from "../shared/data/json-form";
import Validation from "../utils/validation";
import {SearchRequest} from "../shared/data/search-request";
import {HttpClientService} from "../shared/http.client.service";
import {FormDataService} from "../shared/form.data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'json-form-component',
  templateUrl: './json-form-component.html',
  styleUrls: ['./json-form-component.css']
})
export class JsonFormComponent implements OnChanges, OnInit {

  @Input()
  jsonForm: JsonForm = new JsonForm();

  @Output()
  onChange = new EventEmitter<boolean>();

  selectedApis: string[] = [];
  notEnoughFields: boolean = false;
  form: FormGroup = new FormGroup({});
  submitted = false;
  combinations: {[key: string]: string} = {};

  constructor(private httpClientService: HttpClientService, private formDataService: FormDataService, private router: Router) {
  }

  ngOnInit(): void {
    this.jsonForm.combinations.forEach(combination => {
      for (let field of combination.fields) {
        if (this.combinations[combination.apiName] == undefined) {
          this.combinations[combination.apiName] = "Required: "
        }
        if (field.required) {
          this.combinations[combination.apiName] = this.combinations[combination.apiName] + this.normalizeCamelCase(field.field) + ", ";
        }
      }
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    const controls: any = {};

    this.jsonForm.inputs.forEach(input => {
      if(input.type == FieldType.object) {
        let first = true;
        input.components.forEach(component => {
          const control = this.createControl(component, '', input.name);
          controls[input.name + "." + component.name] = control;
          if(first) {
            control.addValidators(Validation.complexFieldLength(input.name, input.maxLength, () => this.form));
            first = false;
          }
        });
      } else {
        const value = input.type == FieldType.select ? input.options[0].value : '';

        controls[input.name] = this.createControl(input, value);
      }
    });

    this.form = new FormGroup(controls);

    const onUpdateCallback = (formData: any) => {
      const data = this.collectData();

      this.selectedApis = [];
      this.jsonForm.combinations.forEach(combination => {
        if(ApiCombination.isValid(combination, data))
          this.selectedApis.push(combination.apiName);
      });

      this.notEnoughFields = this.selectedApis.length == 0;
      if(formData)
        this.formDataService.save(formData);
    }

    this.form.valueChanges.subscribe(onUpdateCallback);
    onUpdateCallback(undefined);
  }

  createControl(field: JsonFormInput, value: string = '', parentName: string | undefined = undefined): FormControl {
    const control = new FormControl();
    control.addValidators(this.getValidators(field));

    const controlName = parentName ? parentName + "." + field.name : field.name;
    const prevValue = this.formDataService.data[controlName];
    control.setValue(prevValue || value);

    return control;
  }

  onSubmit(): void {
    this.submitted = true;

    if(!this.form.valid)
      return;

    const data: any = this.collectData()
    const apis: string[] = [];

    const request: SearchRequest = new SearchRequest(apis, data);
    this.jsonForm.combinations.forEach(combination => {
      if (ApiCombination.isValid(combination, data)) {
        apis.push(combination.apiName);
      }
    });

    if(!request.apis.length) {
      return alert("Not enough fields filled")
    }

    console.log("Data to send", request);

    this.enableLoadingAnimation(true);
    this.httpClientService
      .search(request)
      .subscribe({
        next: value => {
          console.log(value);
          this.router.navigate(['/search/response']);
        },
        error: err => {
          alert(err.error || err.message || err.description || JSON.stringify(err));
        }
      });
  }

  getAllErrors(name: string): string[] {
    const errors: string[] = [];

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

  normalizeCamelCase(str: string): string {
    return str.replace(/([A-Z])/g, ' $1');
  }

  private getValidators(input: JsonFormInput): ValidatorFn[] {
    const result: ValidatorFn[] = [];
    if(input.regex) {
      result.push(Validation.regexValidator(input.regex));
    }

    if(input.type == FieldType.number) {
      result.push(Validation.numberRangeValidator(input.min, input.max))
    }

    return result;
  }

  private collectData(): any {
    const data: any = {};
    this.jsonForm.inputs.forEach(field => {
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

    return data;
  }

  enableLoadingAnimation(value: boolean) {
    this.onChange.emit(value);
  }

  checkAPI(combination: ApiCombination) {
    const result = ApiCombination.isValid(combination, this.collectData());
    console.log(combination, result)
    return result;
  }
}

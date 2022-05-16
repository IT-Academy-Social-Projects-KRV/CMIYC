import {AbstractControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {JsonFormInput} from "../shared/data/json-form";
import {max} from "rxjs";

export default class Validation {
  static match(controlName: string, checkControlName: string): ValidatorFn {
    return (controls: AbstractControl) => {
      const control = controls.get(controlName);
      const checkControl = controls.get(checkControlName);
      if (checkControl?.errors && !checkControl.errors['matching']) {
        return null;
      }
      if (control?.value !== checkControl?.value) {
        controls.get(checkControlName)?.setErrors({ matching: true });
        return { matching: true };
      } else {
        return null;
      }
    };
  }

  static alphanumericValidator: ValidatorFn = control => {
    return /^[A-Za-z0-9]*$/.test(control.value) ? null : {alphabetic: "This field can only contain letters and digits"};
  }

  static alphabeticValidator: ValidatorFn = control => {
    return /^[A-Za-z]*$/.test(control.value) ? null : {alphabetic: "This field can only contain letters"};
  };

  static numericValidator: ValidatorFn = control => {
    return /^[0-9]*$/.test(control.value) ? null : {numeric: "This field can only contain numbers"};
  }

  static regexValidator(regex: string) {
    const res: ValidatorFn = control => {
      const regExp = new RegExp(regex);
      return regExp.test(control.value) ? null : {"regexError": "This field contains bad characters"};
    };

    return res;
  }

  static numberRangeValidator(min: number | undefined, max: number | undefined) {
    const res: ValidatorFn = control => {
      if(min == undefined && max == undefined)
        return null;

      const number = Number.parseFloat(control.value);
      if(min && min > number)
        return {"min": "Min value is: " + min};

      if(max && max < number)
        return {"max": "Max value is: " + max};

      return null;
    };

    return res;
  }

  static complexFieldLength(inputName: string, maxLength: number, formGetter: () => FormGroup) {
    const res: ValidatorFn = control => {
      let length: number = 0;
      const form = formGetter();
      for (let controlsKey in form.controls) {
        if(controlsKey.startsWith(inputName + ".")) {
          const control = form.controls[controlsKey];
          length += control.value.length;
          if(length > maxLength) break;
        }
      }

      return length > maxLength ? {"maxLength": "Max length for this field is " + maxLength + " characters"} : null;
    }

    return res;
  }

  static passwordValidator(): ValidatorFn[] {
    return [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(40),
      Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z0-9$@$!%*?&].*')
    ]
  }

  static nameValidator(): ValidatorFn[] {
    return [
      Validators.minLength(2),
      Validators.maxLength(20),
      Validators.pattern('^[A-Z][-a-zA-Z]+$')
    ]
  }
}

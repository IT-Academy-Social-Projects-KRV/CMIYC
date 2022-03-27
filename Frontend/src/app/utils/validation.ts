import { AbstractControl, ValidatorFn, Validators } from '@angular/forms';

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

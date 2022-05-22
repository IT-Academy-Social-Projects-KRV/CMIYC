import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FormDataService {

  data: any = {};

  constructor() {
  }

  save(data: any) {
    this.data = data;
  }
}

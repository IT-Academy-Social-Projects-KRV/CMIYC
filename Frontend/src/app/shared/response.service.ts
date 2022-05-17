import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import {SearchResponse} from "./data/search.response";

@Injectable({
  providedIn: 'root'
})
export class ResponseService {

  //private response = new BehaviorSubject('Basic Approval is required!');
  // @ts-ignore
  response: SearchResponse;
  constructor() {

  }
  updateResponse(newResponse: SearchResponse) {
    this.response = newResponse;
  }
}

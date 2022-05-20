import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import {SearchResponse} from "./data/search.response";
import {SearchRequest} from "./data/search-request";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  response: SearchResponse | undefined;
  request: SearchRequest | undefined;
  constructor() {

  }
  updateResponse(newResponse: SearchResponse) {
    this.response = newResponse;
  }

  updateRequest(newRequest: SearchRequest) {
    this.request = newRequest;
  }
}

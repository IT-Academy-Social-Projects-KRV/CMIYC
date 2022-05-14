import { Injectable, Injector } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {EnvConfig} from "../../environments/env-model";

@Injectable()
export class EnvService {
  private env: EnvConfig | undefined;

  constructor (private injector: Injector) { }

  loadAppConfig() {
    let http = this.injector.get(HttpClient);

    return http.get('/assets/env.json')
      .toPromise()
      .then(data => {
        this.env = data as EnvConfig;
      })
  }

  get config() {
    return this.env;
  }
}

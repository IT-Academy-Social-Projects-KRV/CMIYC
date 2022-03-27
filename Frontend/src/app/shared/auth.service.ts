import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login<T>(email:string, password:string ): Observable<T> {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa('client-ui:secret'), 'Content-Type': 'application/x-www-form-urlencoded'
    });
    const params = new URLSearchParams();
    params.append( 'username', email);
    params.append( 'password', password);
    params.append( 'grant_type', 'password');
    params.append( 'client_id', 'client-ui');

    return this.http.post<T>('http://localhost:8090/oauth/token', params, {headers});
    // this is just the HTTP call,
    // we still need to handle the reception of the token
  }

}

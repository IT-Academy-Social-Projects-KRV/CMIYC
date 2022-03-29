import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { FormGroup } from '@angular/forms';
import {Observable, of} from "rxjs";
import { Router } from '@angular/router';
import { tap, delay } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor( private router: Router,private http: HttpClient) { }

  isLoggedIn = false;

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

  getSchemas<T>(): Observable<T>  {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('access_token')}`, 'Content-Type': 'application/json'
    });
    return this.http.get<T>('http://localhost:8082/api/search', {headers});
  }

  search<T>(body: FormGroup): Observable<T> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('access_token')}`, 'Content-Type': 'application/json'
    });

    return this.http.post<T>('http://localhost:8082/api/search', JSON.stringify(body.value, null, 2), {headers});
  }

  logout(){
    console.log('logout from authService');
    localStorage.setItem('scope', 'null');
    localStorage.setItem('access_token','null');
    this.router.navigate(['login']);
   return true;

  }
}

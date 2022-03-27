import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }
  isLoggedIn() {
    const token = localStorage.getItem('token'); // get token from local storage
    const payload = atob(token.split('.')[1]); // decode payload of token
    const parsedPayload = JSON.parse(payload); // convert payload into an Object

    return parsedPayload.exp > Date.now()  ; // check if token is expired
}
}

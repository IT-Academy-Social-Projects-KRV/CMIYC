import {Injectable, Injector} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Router} from '@angular/router';
import {LoginRequest} from "./data/login-request";
import {JwtData} from "./data/jwt-data";
import {LoginResult} from "./data/login-result";
import {AuthService, SessionExpiredException, UnauthorizedException} from "./auth.service";
import {User} from './data/user';
import {RequestResult} from "./data/request-result";
import {TfaRequest} from "./data/tfa-request";
import {SchemaFile} from "./data/schema";
import {EnvService} from "./env.service";
import {JsonForm} from "./data/json-form";
import {SearchRequest} from "./data/search-request";
import {SearchResponse} from "./data/search.response";

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  private readonly AUTH_SERVER: string | undefined;
  private readonly SEARCH_API: string | undefined;
  private readonly DATA_API: string | undefined;

  // Auth

  private readonly URL_LOGIN: string;
  private readonly URL_USERS: string;
  private readonly URL_SET_USER_ACTIVE: string;
  private readonly URL_ACTIVATION: string;
  private readonly URL_REGISTRATION: string;
  private readonly URL_PAGINATION: string;
  private readonly URL_USERS_SEARCH: string;


  // Search API
  private readonly URL_SCHEMA: string;
  private readonly URL_SEARCH: string;

  // Data API
  private readonly URL_SCHEMAS: string;
  private readonly URL_SCHEMA_CONTENT: string;
  private readonly URL_SCHEMA_JSON: string;
  private readonly URL_SCHEMA_DELETE: string;
  private readonly URL_SCHEMA_SELECT: string;

  private readonly HEADERS = new HttpHeaders({
    'Authorization': 'Basic ' + btoa('client-ui:secret'),
    'Content-Type': 'application/x-www-form-urlencoded'
  });

  constructor(private router: Router, private http: HttpClient, private injector: Injector, private env: EnvService) {
    this.AUTH_SERVER = env.config?.authServer
    this.SEARCH_API = env.config?.searchAPI
    this.DATA_API = env.config?.dataAPI

    this.URL_LOGIN = this.AUTH_SERVER + '/oauth/token';
    this.URL_USERS = this.AUTH_SERVER + '/users';
    this.URL_SET_USER_ACTIVE = this.AUTH_SERVER + '/users/{userId}/active/';
    this.URL_ACTIVATION = this.AUTH_SERVER + "/users/activation";
    this.URL_REGISTRATION = this.AUTH_SERVER + "/users/registration";
    this.URL_PAGINATION = this.URL_USERS + "/?page={page}&size={itemsAmount}";
    this.URL_USERS_SEARCH = this.URL_USERS + "/search/?page={page}&size={itemsAmount}&firstName={firstName}&lastName={lastName}&isActive={isActive}&email={email}";

    this.URL_SCHEMA = this.SEARCH_API + '/schema';
    this.URL_SEARCH = this.SEARCH_API + '/search';

    this.URL_SCHEMAS = this.DATA_API + '/schemas';
    this.URL_SCHEMA_CONTENT = this.DATA_API + '/schemas/{name}/content';
    this.URL_SCHEMA_JSON = this.DATA_API + '/schemas/{name}/json';
    this.URL_SCHEMA_DELETE = this.DATA_API + '/schemas/{name}';
    this.URL_SCHEMA_SELECT = this.DATA_API + '/schemas/{name}/select'
  }

  private getHeadersWithToken(contentType: string | null): HttpHeaders {
    const authService = this.injector.get(AuthService);

    try {
      const accessToken = authService.validateAndGetToken();

      return contentType == null ?
        new HttpHeaders({
          'Authorization': `Bearer ${accessToken}`,
        }) :
        new HttpHeaders({
          'Authorization': `Bearer ${accessToken}`,
          'Content-Type': contentType
        });
    } catch (e: UnauthorizedException | SessionExpiredException | any) {
      alert(e.message);
      authService.performLogout();
      throw e;
    }
  }

  private getMultipartRequestOptions(): Object {
    return {
      "responseType": "json",
      "headers": this.getHeadersWithToken(null)
    }
  }

  private getJSONRequestOptions(): Object {
    return {
      "responseType": "json",
      "headers": this.getHeadersWithToken('application/json')
    }
  }

  private getPlainTextRequestOptions(): Object {
    return {
      "responseType": "text",
      "headers": this.getHeadersWithToken('application/json')
    }
  }

  private getRequestJSON<T>(url: string): Observable<T> {
    return this.http.get<T>(url, this.getJSONRequestOptions());
  }

  private getRequestText<T>(url: string): Observable<T> {
    return this.http.get<T>(url, this.getPlainTextRequestOptions());
  }


  private postRequest<T>(url: string, params: any): Observable<T> {
    return this.http.post<T>(url, JSON.stringify(params, null, 2), this.getJSONRequestOptions());
  }

  private putRequest<T>(url: string, params: any): Observable<T> {
    return this.http.put<T>(url, JSON.stringify(params, null, 2), this.getJSONRequestOptions());
  }

  private postFile<T>(url: string, formData: FormData): Observable<T> {
    return this.http.post<T>(url, formData, this.getMultipartRequestOptions());
  }

  private deleteRequest<T>(url: string): Observable<T> {
    return this.http.delete<T>(url, this.getJSONRequestOptions());
  }

  public login(email: string, password: string, callback: Function): void {
    const params = new LoginRequest(email, password);
    this.handleLoginRequest(callback, params, this.HEADERS)
  }

  public secondFactor(code: string, callback: Function): void {
    let token = localStorage.getItem("access_token")

    if (token != null) {
      const params = new TfaRequest(code, token);
      this.handleLoginRequest(callback, params, this.HEADERS)
    }
  }

  public getSelectedSchema(): Observable<JsonForm> {
    return this.getRequestJSON(this.URL_SCHEMA);
  }

  public search(requestData: SearchRequest): Observable<SearchResponse> {
    return this.postRequest(this.URL_SEARCH, requestData);
  }

  public getHistory(): Observable<Array<SearchResponse>> {
    return this.getRequestJSON(this.URL_SEARCH + "/history");
  }

  public getUsers(): Observable<User[]> {
    return this.getRequestJSON<User[]>(this.URL_USERS);
  }

  public getInitialPage(itemsAmount: number): Observable<Object> {
    let page = 0;
    return this.getRequestJSON<Object>(this.URL_PAGINATION.replace("{page}", String(page)).replace("{itemsAmount}", String(itemsAmount)));
  }

  public getUsersOnPage(page: number, itemsAmount: number): Observable<Object> {
    let pageOnBack = page - 1;
    return this.getRequestJSON<Object>(this.URL_PAGINATION.replace("{page}", String(pageOnBack)).replace("{itemsAmount}", String(itemsAmount)));
  }

  public getUsersSearchInitialPage(itemsAmount: number, firstName: string, lastName: string, email: string, isActive: boolean): Observable<Object> {
    let page = 0;

    return this.getRequestJSON<Object>(this.URL_USERS_SEARCH
      .replace("{page}", String(page))
      .replace("{itemsAmount}", String(itemsAmount))
      .replace("{firstName}", String(firstName))
      .replace("{lastName}", String(lastName))
      .replace("{email}", String(email))
      .replace("{isActive}", String(isActive))
    );
  }


  public getUsersSearchOnPage(page: number, itemsAmount: number, firstName: string, lastName: string, email: string, isActive: boolean): Observable<Object> {
    let pageOnBack = page - 1;

    return this.getRequestJSON<Object>(this.URL_USERS_SEARCH
      .replace("{page}", String(pageOnBack))
      .replace("{itemsAmount}", String(itemsAmount))
      .replace("{firstName}", String(firstName))
      .replace("{lastName}", String(lastName))
      .replace("{email}", String(email))
      .replace("{isActive}", String(isActive))
    );
  }

  public deleteUser(userId: number): Observable<number> {
    return this.deleteRequest<number>(this.URL_USERS + "/" + userId);
  }

  public setUserActive(userId: number, isActive: boolean): Observable<any> {
    return this.postRequest(
      this.URL_SET_USER_ACTIVE.replace("{userId}", String(userId)) + isActive,
      {}
    );
  }

  public getSchemas(): Observable<SchemaFile[]> {
    return this.getRequestJSON<SchemaFile[]>(this.URL_SCHEMAS);
  }

  public uploadSchema<T>(formData: FormData): Observable<T> {
    return this.postFile(this.URL_SCHEMAS, formData);
  }

  public deleteSchema(name: string) {
    return this.deleteRequest(this.URL_SCHEMA_DELETE.replace("{name}", name));
  }

  public getSchemaContent(name: string): Observable<string> {
    return this.getRequestText<string>(this.URL_SCHEMA_CONTENT.replace("{name}", name));
  }

  public getSchemaJSON(name: string): Observable<any> {
    return this.getRequestJSON<any>(this.URL_SCHEMA_JSON.replace("{name}", name));
  }

  public selectSchema(name: string): Observable<any> {
    return this.postRequest(this.URL_SCHEMA_SELECT.replace("{name}", name), {});
  }

  public activateUser(token: string, password: string, confirmPassword: string, captcha : string, callback: (result: RequestResult) => void) {
    const data = {
      'token': token,
      'password': password,
      'confirmPassword': confirmPassword,
      'captchaResponse': captcha
    }

    this.http
      .post(this.URL_ACTIVATION, data, {headers: new HttpHeaders({'Content-Type': 'application/json'})})
      .subscribe({
        next: () => {
          callback(RequestResult.success("Ok"))
        },
        error: (err) => {
          callback(RequestResult.error(
            err.error.error_description ||
            err.error.error ||
            err.error ||
            "Unexpected error occurred. Please, try again later"))
        }
      });

  }

  public createUser(firstName: string, lastName: string, email: string, roles: (string | null)[], callback: (result: RequestResult) => void) {
    const data = {
      'firstName': firstName,
      'lastName': lastName,
      'email': email,
      'roles': roles
    }

    this.postRequest<any>(this.URL_REGISTRATION, data)
      .subscribe({
        next: (res) => {
          callback(RequestResult.success(" "))
        },
        error: (err) => {
          callback(RequestResult.error(
            err.error))
        }
      });
  }

  public updateUser(id: number, data: any, callback: (result: RequestResult) => void) {

    this.putRequest<any>(this.URL_USERS + "/" + id, data)
      .subscribe({
        next: (res) => {
          const error = res['error'];
          const message = res['message'];
          callback(error ? RequestResult.error(message) : RequestResult.success(message))
        },
        error: (err) => {
          callback(RequestResult.error(
            err.error.error_description ||
            err.error.error ||
            "Unexpected error occurred. Please, try again later"))
        }
      });
  }

  private handleLoginRequest(callback: Function, params: URLSearchParams, headers: HttpHeaders) {
    this
      .http
      .post<JwtData>(this.URL_LOGIN, params, {headers})
      .subscribe({
        next: jwtData => {
          callback.call(this, LoginResult.success(jwtData))
        },
        error: err => {
          callback.call(this, LoginResult.error(err.error.error_description || "Server is temporary unavailable. Please, try again later!"));
        }
      });
  }
}

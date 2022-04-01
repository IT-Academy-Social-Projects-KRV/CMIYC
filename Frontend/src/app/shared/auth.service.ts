import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClientService} from "./http.client.service";
import {LoginResult} from "./data/login-result";
import {JwtData} from "./data/jwt-data";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly SCOPE_ADMIN_USER = "admin_user";
  private readonly SCOPE_ADMIN_SCHEMA = "admin_schema";
  private readonly SCOPE_USER = "user";

  constructor(private router: Router, private httpClientService: HttpClientService) {
  }

  // TODO: rename this method
  private isScopeAuthenticated(scope: string) {
    const scopes = localStorage.getItem("scopes");
    return scopes != null && scopes.split(", ").filter(s => s === scope).length > 0;
  }

  private saveJwtDataToStorage(jwtData: JwtData): void {
    localStorage.setItem('access_token', jwtData.access_token);
    localStorage.setItem('scopes', jwtData.scope);
    localStorage.setItem('full_name', jwtData.fullName);
  }

  private removeJwtDataFromStorage() {
    localStorage.removeItem("access_token");
    localStorage.removeItem("scopes");
    localStorage.removeItem("full_name");
  }

  private getUrlToNavigateAfterLogin(): string {
    switch (true) {
      case this.isUserAdminLoggedIn(): {
        return 'admin/allUsers';
      }
      case this.isSchemaAdminLoggedIn(): {
        return 'admin/allSchemas';
      }
      default: {
        return 'userSearch';
      }
    }
  }

  public performLogin(email: string, password: string): void {
    this.httpClientService.login(email, password, (loginResult: LoginResult) => {
      if(loginResult.isError || loginResult.jwtData == null) {
        alert(loginResult.errorMessage);
        this.router.navigate(['login']);
      } else {
        const response: JwtData | null = loginResult.jwtData;
        this.saveJwtDataToStorage(response);
        this.router.navigate([this.getUrlToNavigateAfterLogin()]);
      }
    });
  }

  public performLogout() {
    this.removeJwtDataFromStorage();
    this.router.navigate(['login']);
  }

  public isUserAdminLoggedIn(): boolean {
    return this.isScopeAuthenticated(this.SCOPE_ADMIN_USER);
  }

  public isSchemaAdminLoggedIn(): boolean {
    return this.isScopeAuthenticated(this.SCOPE_ADMIN_SCHEMA);
  }

  public isUserLoggedIn(): boolean {
    return this.isScopeAuthenticated(this.SCOPE_USER);
  }
}

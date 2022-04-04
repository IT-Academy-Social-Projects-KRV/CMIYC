import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClientService} from "./http.client.service";
import {LoginResult} from "./data/login-result";
import {JwtData} from "./data/jwt-data";

export class UnauthorizedException implements Error {
  readonly message: string = "You are unauthorized. Please log in.";
  readonly name: string = "UnauthorizedException";
}

export class SessionExpiredException implements Error {
  readonly message: string = "Your session has expired. Please log in again.";
  readonly name: string = "SessionExpiredException";
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly SCOPE_ADMIN_USER = "admin_user";
  private readonly SCOPE_ADMIN_SCHEMA = "admin_schema";
  private readonly SCOPE_USER = "user";

  constructor(private router: Router, private httpClientService: HttpClientService) {
  }

  private isScopeAuthenticated(scope: string) {
    const scopes = localStorage.getItem("scopes");
    return scopes != null && scopes.split(", ").filter(s => s === scope).length > 0;
  }

  private saveJwtDataToStorage(jwtData: JwtData): void {
    localStorage.setItem('access_token', jwtData.access_token);
    localStorage.setItem('scopes', jwtData.scope);
    localStorage.setItem('full_name', jwtData.fullName);

    const expiresAt = new Date(new Date().getTime() + (jwtData.expires_in - 5) * 1000);
    localStorage.setItem('expires_at', expiresAt.toString());
  }

  private removeJwtDataFromStorage() {
    localStorage.removeItem("access_token");
    localStorage.removeItem("scopes");
    localStorage.removeItem("full_name");
    localStorage.removeItem("expires_at");
  }

  public getUrlToNavigateAfterLogin(): string {
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
      if (loginResult.isError || loginResult.jwtData == null) {
        alert(loginResult.errorMessage);
        this.router.navigate(['login']);
      } else {
        const response: JwtData | null = loginResult.jwtData;
        this.saveJwtDataToStorage(response);
        this.router.navigate([this.getUrlToNavigateAfterLogin()]);
      }
    });
  }

  // Returns accessToken
  // Throws error if token is absent or expired so the calling class can handle this
  public validateAndGetToken(): string {
    const accessToken = localStorage.getItem('access_token');
    const expiresAtString = localStorage.getItem('expires_at');

    if (accessToken == null || expiresAtString == null)
      throw new UnauthorizedException();

    const expiresAt = new Date(expiresAtString);
    // Якщо час дії токену вийшов, викидаємо помилку
    if (expiresAt.getTime() < new Date().getTime())
      throw new SessionExpiredException();

    return accessToken;
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

  public isAnyUserLoggedIn(): boolean {
    return localStorage.getItem("access_token") != null;
  }
}

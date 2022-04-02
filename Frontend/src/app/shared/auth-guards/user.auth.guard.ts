import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from '../auth.service';
import {AbstractAuthGuard} from "./abstract.auth.guard";

@Injectable({
  providedIn: 'root'
})
export class UserAuthGuard extends AbstractAuthGuard {
  constructor(private authService: AuthService, router: Router) {
    super(router, () => authService.isUserLoggedIn(), "login");
  }
}

import {AuthService} from "../auth.service";

export class User {
  // Data from server
  readonly id: number;
  readonly email: string;
  readonly firstName: string;
  readonly lastName: string;
  active: boolean;
  readonly registerDate: string;
  readonly scopes: string[];

  // Generated in constructor
  readonly isUserAdmin: boolean;
  readonly isSchemaAdmin: boolean;
  readonly isUser: boolean;

  constructor(id: number, email: string, firstName: string, lastName: string, active: boolean, registerDate: string, scopes: string[]) {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.active = active;
    this.registerDate = registerDate;
    this.scopes = scopes;

    this.isUserAdmin = scopes.filter(value => value === AuthService.SCOPE_ADMIN_USER).length > 0;
    this.isSchemaAdmin = scopes.filter(value => value === AuthService.SCOPE_ADMIN_SCHEMA).length > 0;
    this.isUser = scopes.filter(value => value === AuthService.SCOPE_USER).length > 0;
  }
}
